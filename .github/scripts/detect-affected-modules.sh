#!/bin/bash

# Script to detect affected modules based on Git changes and output Gradle test tasks
# Usage: ./detect-affected-modules.sh [base_commit]
# Default base_commit: origin/main

set -euo pipefail

# Configuration
BASE_COMMIT="${1:-origin/main}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1" >&2
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1" >&2
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1" >&2
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1" >&2
}

# Function to get all changed files
get_changed_files() {
    local base_commit="$1"
    
    # Check if we're in a Git repository
    if ! git rev-parse --git-dir > /dev/null 2>&1; then
        log_error "Not in a Git repository"
        exit 1
    fi
    
    # Check if base commit exists
    if ! git rev-parse --verify "$base_commit" > /dev/null 2>&1; then
        log_error "Base commit '$base_commit' does not exist"
        exit 1
    fi
    
    # Get changed files (including renamed/moved files)
    git diff --name-only "$base_commit"...HEAD 2>/dev/null || {
        log_error "Failed to get diff from $base_commit"
        exit 1
    }
}

# Function to define module structure and dependencies
get_module_info() {
    # Define all modules with their paths and dependencies
    # Format: "module_name:gradle_path:directory_path:dependencies"
    cat << 'EOF'
auth:auth:auth/:common
bom:bom:bom/:
buildlogic:buildlogic:buildlogic/:
common:common:common/:
eventproducer:eventproducer:eventproducer/:common,auth
player:player:player/:auth,eventproducer,common
player-common:player:common:player/common/:common
player-common-android:player:common-android:player/common-android/:player-common
player-events:player:events:player/events/:player-common
player-playback-engine:player:playback-engine:player/playback-engine/:player-common
player-streaming-api:player:streaming-api:player/streaming-api/:player-common
player-streaming-privileges:player:streaming-privileges:player/streaming-privileges/:player-common
player-testutil:player:testutil:player/testutil/:player-common
tidalapi:tidalapi:tidalapi/:common
template:template:template/:
EOF
}

# Function to map file paths to modules
map_files_to_modules() {
    local changed_files=("$@")
    local affected_modules=()
    
    log_info "Mapping ${#changed_files[@]} changed files to modules..."
    
    # Get module information
    local module_info
    module_info=$(get_module_info)
    
    for file in "${changed_files[@]}"; do
        log_info "Processing file: $file"
        
        # Skip empty lines
        [[ -z "$file" ]] && continue
        
        # Check for root-level changes that affect all modules
        if [[ "$file" =~ ^(build\.gradle\.kts|settings\.gradle\.kts|gradle\.properties|gradlew|gradlew\.bat)$ ]] || \
           [[ "$file" =~ ^gradle/ ]] || \
           [[ "$file" =~ ^buildlogic/ ]] || \
           [[ "$file" =~ ^\.github/workflows/ ]]; then
            log_warn "Root-level change detected: $file - will run all tests"
            echo "ALL_TESTS"
            return 0
        fi
        
        # Map file to module
        local module_found=false
        while IFS=':' read -r module_name gradle_path dir_path dependencies; do
            if [[ "$file" =~ ^"$dir_path" ]]; then
                affected_modules+=("$module_name")
                module_found=true
                log_info "  -> Mapped to module: $module_name"
                break
            fi
        done <<< "$module_info"
        
        if [[ "$module_found" == false ]]; then
            log_warn "  -> No module mapping found for: $file"
        fi
    done
    
    # Remove duplicates and sort
    if [[ ${#affected_modules[@]} -gt 0 ]]; then
        printf '%s\n' "${affected_modules[@]}" | sort -u
    fi
}

# Function to get dependent modules
get_dependent_modules() {
    local directly_affected=("$@")
    local all_affected=()
    local module_info
    module_info=$(get_module_info)
    
    # Add directly affected modules
    all_affected+=("${directly_affected[@]}")
    
    # Find modules that depend on the affected ones
    for affected_module in "${directly_affected[@]}"; do
        log_info "Finding modules that depend on: $affected_module"
        
        while IFS=':' read -r module_name gradle_path dir_path dependencies; do
            # Skip empty dependencies
            [[ -z "$dependencies" ]] && continue
            
            # Check if this module depends on the affected module
            if [[ ",$dependencies," =~ ,$affected_module, ]]; then
                all_affected+=("$module_name")
                log_info "  -> Found dependent module: $module_name"
            fi
        done <<< "$module_info"
    done
    
    # Remove duplicates and sort
    if [[ ${#all_affected[@]} -gt 0 ]]; then
        printf '%s\n' "${all_affected[@]}" | sort -u
    fi
}

# Function to convert module names to Gradle test tasks
modules_to_gradle_tasks() {
    local modules=("$@")
    local gradle_tasks=()
    local module_info
    module_info=$(get_module_info)
    
    for module in "${modules[@]}"; do
        while IFS=':' read -r module_name gradle_path dir_path dependencies; do
            if [[ "$module_name" == "$module" ]]; then
                # Add test task for the module
                gradle_tasks+=(":${gradle_path}:test")
                
                # Add demo app test if it exists
                if [[ -d "$PROJECT_ROOT/$dir_path/apps/demo" ]]; then
                    gradle_tasks+=(":${gradle_path}:apps:demo:test")
                fi
                break
            fi
        done <<< "$module_info"
    done
    
    # Remove duplicates and sort
    if [[ ${#gradle_tasks[@]} -gt 0 ]]; then
        printf '%s\n' "${gradle_tasks[@]}" | sort -u
    fi
}

# Function to get all test tasks
get_all_test_tasks() {
    local module_info
    module_info=$(get_module_info)
    local all_tasks=()
    
    while IFS=':' read -r module_name gradle_path dir_path dependencies; do
        # Skip buildlogic and template modules for testing
        if [[ "$module_name" == "buildlogic" ]] || [[ "$module_name" == "template" ]]; then
            continue
        fi
        
        all_tasks+=(":${gradle_path}:test")
        
        # Add demo app test if it exists
        if [[ -d "$PROJECT_ROOT/$dir_path/apps/demo" ]]; then
            all_tasks+=(":${gradle_path}:apps:demo:test")
        fi
    done <<< "$module_info"
    
    printf '%s\n' "${all_tasks[@]}" | sort -u
}

# Main execution
main() {
    log_info "Detecting affected modules for changes since: $BASE_COMMIT"
    
    cd "$PROJECT_ROOT"
    
    # Get changed files
    local changed_files_raw
    changed_files_raw=$(get_changed_files "$BASE_COMMIT")
    
    if [[ -z "$changed_files_raw" ]]; then
        log_info "No changes detected since $BASE_COMMIT"
        exit 0
    fi
    
    # Convert to array
    local changed_files=()
    while IFS= read -r line; do
        [[ -n "$line" ]] && changed_files+=("$line")
    done <<< "$changed_files_raw"
    
    log_info "Found ${#changed_files[@]} changed files"
    
    # Map files to modules
    local affected_modules_raw
    affected_modules_raw=$(map_files_to_modules "${changed_files[@]}")
    
    # Check if we need to run all tests
    if [[ "$affected_modules_raw" == "ALL_TESTS" ]]; then
        log_warn "Root-level changes detected - running all tests"
        local all_tasks
        all_tasks=$(get_all_test_tasks)
        echo "$all_tasks" | tr '\n' ' '
        return 0
    fi
    
    if [[ -z "$affected_modules_raw" ]]; then
        log_info "No modules affected by the changes"
        exit 0
    fi
    
    # Convert to array
    local directly_affected=()
    while IFS= read -r line; do
        [[ -n "$line" ]] && directly_affected+=("$line")
    done <<< "$affected_modules_raw"
    
    log_info "Directly affected modules: ${directly_affected[*]}"
    
    # Get all affected modules (including dependents)
    local all_affected_raw
    all_affected_raw=$(get_dependent_modules "${directly_affected[@]}")
    
    local all_affected=()
    while IFS= read -r line; do
        [[ -n "$line" ]] && all_affected+=("$line")
    done <<< "$all_affected_raw"
    
    log_info "All affected modules (including dependents): ${all_affected[*]}"
    
    # Convert to Gradle tasks
    local gradle_tasks_raw
    gradle_tasks_raw=$(modules_to_gradle_tasks "${all_affected[@]}")
    
    if [[ -z "$gradle_tasks_raw" ]]; then
        log_info "No test tasks to run"
        exit 0
    fi
    
    # Output the final result (space-separated for easy consumption)
    local gradle_tasks=()
    while IFS= read -r line; do
        [[ -n "$line" ]] && gradle_tasks+=("$line")
    done <<< "$gradle_tasks_raw"
    
    log_success "Test tasks to run: ${gradle_tasks[*]}"
    echo "${gradle_tasks[*]}"
}

# Handle script arguments
case "${1:-}" in
    -h|--help)
        cat << 'EOF'
Usage: detect-affected-modules.sh [base_commit]

Detects affected modules based on Git changes and outputs Gradle test tasks.

Arguments:
  base_commit    Base commit/branch to compare against (default: origin/main)

Options:
  -h, --help     Show this help message

Examples:
  ./detect-affected-modules.sh
  ./detect-affected-modules.sh origin/develop
  ./detect-affected-modules.sh HEAD~1

Output:
  Space-separated list of Gradle test tasks to run, e.g.:
  :auth:test :eventproducer:test :player:test
EOF
        exit 0
        ;;
    *)
        main "$@"
        ;;
esac
