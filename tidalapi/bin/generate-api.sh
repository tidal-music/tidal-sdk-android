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
    echo ""
    echo "If no options are provided, the script will use the URL specified in generate-api-config.json"
    exit 1
}

# Parse command line arguments
LOCAL_FILE=""
CUSTOM_URL=""

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
        -h|--help)
            usage
            ;;
        *)
            echo "Unknown option: $1"
            usage
            ;;
    esac
done

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check for required commands
if ! command_exists python3; then
    echo "Error: python3 is required but not installed."
    exit 1
fi

if ! command_exists java; then
    echo "Error: java is required but not installed."
    exit 1
fi

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
    if [ ! -f "$LOCAL_FILE" ]; then
        echo "Error: Local file '$LOCAL_FILE' not found."
        deactivate
        exit 1
    fi
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