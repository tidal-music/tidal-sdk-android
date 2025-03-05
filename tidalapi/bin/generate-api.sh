#!/bin/bash

# Exit on error
set -e

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Change to the script directory
cd "$SCRIPT_DIR"

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

# Run the generation script
python3 generate-api-files.py generate-api-config.json

# Deactivate virtual environment
deactivate

echo "API generation completed successfully!"