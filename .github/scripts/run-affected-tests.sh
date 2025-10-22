#!/bin/bash

# Wrapper script to detect affected modules and run their tests
# Usage: ./run-affected-tests.sh [base_commit] [additional_gradle_args...]

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
DETECT_SCRIPT="$SCRIPT_DIR/detect-affected-modules.sh"

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Parse arguments
BASE_COMMIT="${1:-origin/main}"
shift || true
GRADLE_ARGS=("$@")

echo -e "${BLUE}🔍 Detecting affected modules...${NC}"

# Detect affected test tasks
TEST_TASKS=$("$DETECT_SCRIPT" "$BASE_COMMIT")

if [[ -z "$TEST_TASKS" ]]; then
    echo -e "${YELLOW}ℹ️  No tests to run - no modules affected${NC}"
    exit 0
fi

echo -e "${GREEN}📋 Test tasks to run:${NC} $TEST_TASKS"

# Change to project root
cd "$PROJECT_ROOT"

# Run the tests
echo -e "${BLUE}🧪 Running affected tests...${NC}"

if ./gradlew $TEST_TASKS "${GRADLE_ARGS[@]}" --continue; then
    echo -e "${GREEN}✅ All affected tests passed!${NC}"
else
    echo -e "\033[0;31m❌ Some tests failed!${NC}"
    exit 1
fi
