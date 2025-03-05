# API Generation Scripts

This directory contains scripts for generating the TIDAL API code from OpenAPI specifications.

## Prerequisites

- Python 3.x
- Java Runtime Environment (JRE)

## Quick Start

To generate the API code, simply run:

```bash
./generate-api.sh
```

This script will:
1. Create a Python virtual environment if it doesn't exist
2. Install required dependencies
3. Run the API generation process
4. Clean up temporary files

## Manual Generation

If you need more control over the process, you can run the steps manually:

1. Create and activate virtual environment:
   ```bash
   python3 -m venv venv
   source venv/bin/activate
   ```

2. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```

3. Run the generation script:
   ```bash
   python generate-api-files.py generate-api-config.json
   ```

## Files

- `generate-api.sh` - Main wrapper script for easy API generation
- `generate-api-files.py` - Python script that handles the API generation process
- `generate-api-config.json` - Configuration file for API generation
- `openapi-generator-cli.jar` - OpenAPI Generator tool
- `requirements.txt` - Python dependencies

## Generated Code

The generated API code will be placed in:
`../src/main/kotlin/com/tidal/sdk/tidalapi/generated/`