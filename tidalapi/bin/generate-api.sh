#!/bin/bash

# Exit on error
set -e

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Change to the script directory
cd "$SCRIPT_DIR"

# Display usage information
usage() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help                 Show this help message"
    echo "  -l, --local-file <path>    Use a local OpenAPI JSON file instead of downloading"
    echo "  -u, --url <url>            Use a specific URL to download the OpenAPI JSON file"
    echo "  -b, --bump                 Bump the module version and update changelog after generation"
    echo ""
    echo "If no options are provided, the script will use the URL specified in generate-api-config.json"
    exit 1
}

# Parse command line arguments
LOCAL_FILE=""
CUSTOM_URL=""
BUMP_VERSION="false"

while [[ $# -gt 0 ]]; do
    key="$1"
    case $key in
        -l|--local-file)
            LOCAL_FILE="$2"
            shift 2
            ;;
        -u|--url)
            CUSTOM_URL="$2"
            shift 2
            ;;
        -b|--bump)
            BUMP_VERSION="true"
            shift
            ;;
        -h|--help)
            usage
            ;;
        *)
            echo "Unknown option: $1"
            usage
            ;;
    esac
done

# Function to print error message and exit
die() { echo "Error: $*" >&2; exit 1; }

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to extract API version from JSON file
get_api_version() {
    local json_file="$1"
    if [[ -f "$json_file" ]]; then
        jq -r '.info.version // "unknown"' "$json_file"
    else
        echo "unknown"
    fi
}

# Function to bump patch version
bump_patch_version() {
    local current_version=$1
    # Validate and capture parts
    if [[ $current_version =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]; then
        printf '%d.%d.%d\n' \
            "$((10#${BASH_REMATCH[1]}))" \
            "$((10#${BASH_REMATCH[2]}))" \
            "$((10#${BASH_REMATCH[3]} + 1))"
    else
        printf 'Error: Invalid version format: %s\n' "$current_version" >&2
        return 1
    fi
}

# Function to update gradle.properties version
update_version_in_gradle_properties() {
    local new_version="$1"
    local gradle_properties="../gradle.properties"
    
    [[ -f "$gradle_properties" ]] || { echo "Error: gradle.properties not found at $gradle_properties" >&2; return 1; }
    
    # Update version in gradle.properties using sed
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        sed -i '' "s/^version=.*/version=${new_version}/" "$gradle_properties"
    else
        # Linux
        sed -i "s/^version=.*/version=${new_version}/" "$gradle_properties"
    fi
    echo "Updated version to $new_version in gradle.properties"
}

# Function to update CHANGELOG.md
update_changelog() {
    local new_version="$1"
    local api_version="$2"
    local changelog_file="../CHANGELOG.md"
    local current_date=$(date +"%Y-%m-%d")

    [[ -f "$changelog_file" ]] || { echo "Error: CHANGELOG.md not found at $changelog_file" >&2; return 1; }
    
    # Create temporary file for new changelog content
    local temp_file=$(mktemp)
    
    # Read the current changelog and insert new entry after [Unreleased]
    awk -v new_version="$new_version" -v api_version="$api_version" -v current_date="$current_date" '
    !done && /^## \[Unreleased]$/ {
        print
        print ""
        printf "## [%s] - %s\n", new_version, current_date
        print "### Changed"
        printf "- Updated generated code using api spec version %s\n", api_version
        done=1
        next
    }
    { print }
    ' "$changelog_file" > "$temp_file"
    
    # Replace original file
    mv "$temp_file" "$changelog_file"
    echo "Updated CHANGELOG.md with version $new_version"
}

# Check for required commands
command_exists python3 || die "python3 is required but not installed."
command_exists java || die "java is required but not installed."

echo "Setting up Python virtual environment..."

# Create virtual environment if it doesn't exist
if [ ! -d "venv" ]; then
    python3 -m venv venv
fi

# Activate virtual environment
source venv/bin/activate

# Install/upgrade pip
python3 -m pip install --upgrade pip

# Install requirements
if [ ! -f "requirements.txt" ]; then
    echo "requests>=2.31.0" > requirements.txt
fi
pip install -r requirements.txt

echo "Running API generation script..."

# Prepare command arguments for the Python script
ARGS=("generate-api-config.json")

if [ -n "$LOCAL_FILE" ]; then
    # Check if the local file exists
    [[ -f "$LOCAL_FILE" ]] || die "Local file '$LOCAL_FILE' not found."
    echo "Using local file: $LOCAL_FILE"
    ARGS+=("--local-file" "$LOCAL_FILE")
elif [ -n "$CUSTOM_URL" ]; then
    echo "Using custom URL: $CUSTOM_URL"
    ARGS+=("--url" "$CUSTOM_URL")
fi

# Run the generation script with the appropriate arguments
python3 generate-api-files.py "${ARGS[@]}"

# Deactivate virtual environment
deactivate

echo "API generation completed successfully!"

# Handle version bumping if requested
if [ "$BUMP_VERSION" = "true" ]; then
    echo "Bumping version and updating changelog..."
    
    # Determine the API spec file to extract version from
    API_SPEC_FILE=""
    if [ -n "$LOCAL_FILE" ]; then
        API_SPEC_FILE="$LOCAL_FILE"
    elif [ -f "tidal-api.json" ]; then
        API_SPEC_FILE="tidal-api.json"
    elif [ -f "openapi_downloads/tidal-api-oas.json" ]; then
        API_SPEC_FILE="openapi_downloads/tidal-api-oas.json"
    else
        die "Could not find API spec file to extract version"
    fi
    
    # Extract API version
    API_VERSION=$(get_api_version "$API_SPEC_FILE")
    [[ "$API_VERSION" != "unknown" ]] || die "Could not extract API version from $API_SPEC_FILE"
    echo "API spec version: $API_VERSION"
    
    # Get current module version from gradle.properties
    CURRENT_VERSION=$(grep "^version=" "../gradle.properties" | cut -d'=' -f2)
    [[ -n "$CURRENT_VERSION" ]] || die "Could not read current version from gradle.properties"
    echo "Current module version: $CURRENT_VERSION"
    
    # Bump patch version
    NEW_VERSION=$(bump_patch_version "$CURRENT_VERSION")
    echo "New module version: $NEW_VERSION"
    
    # Update gradle.properties
    update_version_in_gradle_properties "$NEW_VERSION"
    
    # Update CHANGELOG.md
    update_changelog "$NEW_VERSION" "$API_VERSION"
    
    echo "Version bump completed successfully!"
fi
