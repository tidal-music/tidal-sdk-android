# API Generation Scripts

This directory contains scripts for generating the TIDAL API code from OpenAPI specifications.

## Prerequisites

- Python 3.x
- Java Runtime Environment (JRE)

## Quick Start

To generate the API code using the default URL from the config file:

```bash
./generate-api.sh
```

To generate the API code from a local OpenAPI JSON file:

```bash
./generate-api.sh --local-file /path/to/your/openapi.json
```

To generate the API code from a specific URL:

```bash
./generate-api.sh --url https://example.com/your-custom-openapi.json
```

## Options

```
Usage: ./generate-api.sh [options]
Options:
  -h, --help                 Show this help message
  -l, --local-file <path>    Use a local OpenAPI JSON file instead of downloading
  -u, --url <url>            Use a specific URL to download the OpenAPI JSON file
```

## What the Script Does

This script will:
1. Create a Python virtual environment if it doesn't exist
2. Install required dependencies
3. Run the API generation process (either downloading from URL or using a local file)
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
   # Using default URL from config
   python generate-api-files.py generate-api-config.json
   
   # Using local file
   python generate-api-files.py generate-api-config.json --local-file /path/to/your/openapi.json
   
   # Using custom URL
   python generate-api-files.py generate-api-config.json --url https://example.com/your-custom-openapi.json
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