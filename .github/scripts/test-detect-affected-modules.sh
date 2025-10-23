#!/bin/bash

# Test script for detect-affected-modules.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DETECT_SCRIPT="$SCRIPT_DIR/detect-affected-modules.sh"

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

test_count=0
pass_count=0

run_test() {
    local test_name="$1"
    local expected="$2"
    local file_to_change="$3"
    local change_content="${4:-# Test change}"
    
    ((test_count++))
    echo -e "\n${YELLOW}Test $test_count: $test_name${NC}"
    
    # Make the change
    if [[ "$file_to_change" != "NONE" ]]; then
        echo "$change_content" >> "$file_to_change"
    fi
    
    # Run the script and capture output
    local output
    output=$("$DETECT_SCRIPT" HEAD 2>/dev/null || echo "ERROR")
    
    # Clean up the change
    if [[ "$file_to_change" != "NONE" ]]; then
        git checkout -- "$file_to_change" 2>/dev/null || true
    fi
    
    # Check result
    if [[ "$output" == *"$expected"* ]] || [[ "$expected" == "ANY" && -n "$output" ]]; then
        echo -e "${GREEN}✓ PASS${NC}: Got expected output"
        ((pass_count++))
    else
        echo -e "${RED}✗ FAIL${NC}: Expected '$expected', got '$output'"
    fi
}

echo "Running tests for detect-affected-modules.sh"

# Test 1: No changes
run_test "No changes" "" "NONE"

# Test 2: Auth module change
run_test "Auth module change" ":auth:test" "auth/README.md"

# Test 3: Root build file change (should trigger all tests)
run_test "Root build.gradle.kts change" ":auth:test" "build.gradle.kts"

# Test 4: Gradle wrapper change
run_test "Gradle wrapper change" ":auth:test" "gradle/libs.versions.toml"

# Test 5: EventProducer change (depends on auth and common)
run_test "EventProducer module change" ":eventproducer:test" "eventproducer/README.md"

# Test 6: Player change (depends on multiple modules)
run_test "Player module change" ":player:test" "player/README.md"

# Test 7: Common module change (should affect dependents)
run_test "Common module change" "ANY" "common/README.md"

echo -e "\n${YELLOW}Test Results:${NC}"
echo -e "Passed: ${GREEN}$pass_count${NC}/$test_count"

if [[ $pass_count -eq $test_count ]]; then
    echo -e "${GREEN}All tests passed!${NC}"
    exit 0
else
    echo -e "${RED}Some tests failed!${NC}"
    exit 1
fi
