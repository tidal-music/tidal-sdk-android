import json
import logging
import os
import requests
import shutil
import subprocess
import sys
from collections import OrderedDict


def setup_logging():
    logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


def download_file(url, dest_folder):
    local_filename = os.path.join(dest_folder, url.split('/')[-1])
    logging.info(f"Starting download of {url}")
    with requests.get(url, stream=True) as r:
        r.raise_for_status()
        with open(local_filename, 'wb') as f:
            for chunk in r.iter_content(chunk_size=8192):
                f.write(chunk)
    logging.info(f"Downloaded {url} to {local_filename}")
    return local_filename


def remove_specific_line_from_files(directory, target_string):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.kt'):
                file_path = os.path.join(root, file)
                with open(file_path, 'r') as f:
                    lines = f.readlines()

                found = False

                with open(file_path, 'w') as f:
                    for line in lines:
                        if line.startswith(target_string):
                            logging.info(f"Found and removed line in file: {file_path}")
                            found = True
                        else:
                            f.write(line)

                if not found:
                    logging.info(
                        f"No lines starting with '{target_string}' found in file: {file_path}")


def merge_openapi_schemas(file_paths, output_path):
    merged_schema = None
    seen_schemas = set()

    for file_path in file_paths:
        with open(file_path, 'r') as f:
            schema = json.load(f, object_pairs_hook=OrderedDict)

            if merged_schema is None:
                logging.info(f"Initializing merged schema from {file_path}")
                merged_schema = schema
            else:
                for component_type in ['schemas', 'responses', 'parameters', 'examples',
                                       'requestBodies',
                                       'headers', 'securitySchemes', 'links', 'callbacks']:
                    if component_type in schema.get('components', {}):
                        if component_type not in merged_schema['components']:
                            merged_schema['components'][component_type] = OrderedDict()
                        for key, value in schema['components'][component_type].items():
                            if key not in seen_schemas:
                                merged_schema['components'][component_type][key] = value
                                seen_schemas.add(key)
                            else:
                                logging.info(f"Skipping duplicate schema {key} in {file_path}")

                for path, path_item in schema.get('paths', {}).items():
                    if 'paths' not in merged_schema:
                        merged_schema['paths'] = OrderedDict()
                    if path not in merged_schema['paths']:
                        merged_schema['paths'][path] = path_item
                        logging.info(f"Adding path {path} from {file_path}")

    final_schema = json.loads(json.dumps(merged_schema))

    with open(output_path, 'w') as f:
        json.dump(final_schema, f, indent=2)
        logging.info(f"Merged schema saved to {output_path}")


def create_merged_schema(json_file_path):
    try:
        with open(json_file_path, 'r') as f:
            config = json.load(f)
    except Exception as e:
        logging.error(f"Failed to read JSON file {json_file_path}: {e}")
        sys.exit(1)

    inputs = config.get('inputs', [])
    if not inputs:
        logging.error("No input files specified in the configuration.")
        sys.exit(1)

    output_path = config.get('output')
    if not output_path:
        logging.error("No output file specified in the configuration.")
        sys.exit(1)

    # Extract openapi-generator-cli.jar path from JSON config
    openapi_generator_path = config.get('openapi_generator_path')
    if not openapi_generator_path:
        logging.error("No openapi-generator-cli.jar path specified in the configuration.")
        sys.exit(1)

    temp_dir = os.path.join(os.getcwd(), 'openapi_downloads')
    os.makedirs(temp_dir, exist_ok=True)
    logging.info(f"Created directory {temp_dir}")

    downloaded_files = []

    # Download each file
    for input_info in inputs:
        url = input_info.get('inputURL')
        if url:
            try:
                local_file = download_file(url, temp_dir)
                downloaded_files.append(local_file)
            except Exception as e:
                logging.error(f"Error downloading {url}: {e}")

    # Merge downloaded OpenAPI schemas
    try:
        merge_openapi_schemas(downloaded_files, output_path)
    except Exception as e:
        logging.error(f"Error merging schemas: {e}")

    # Clean up downloaded files
    for file_path in downloaded_files:
        try:
            os.remove(file_path)
            logging.info(f"Deleted file: {file_path}")
        except Exception as e:
            logging.error(f"Failed to delete file {file_path}: {e}")

    return output_path, openapi_generator_path  # Return the merged schema path and generator path


def main():
    setup_logging()

    project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
    sdk_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '../..'))

    if len(sys.argv) < 2:
        logging.info("Error: You must provide the path to the config file.")
        logging.info(f"Usage: {sys.argv[0]} <path to config.json>")
        sys.exit(1)

    config_file = sys.argv[1]

    # Create the merged schema and get the path to the OpenAPI generator jar
    temp_json, source = create_merged_schema(config_file)

    # Define the generated files path and clean the directory
    generated_files_dir = os.path.join(project_root,
                                       "src/main/kotlin/com/tidal/sdk/tidalapi/generated")

    # Clean the directory
    if os.path.exists(generated_files_dir):
        shutil.rmtree(generated_files_dir)
        logging.info(f"Cleaned up directory: {generated_files_dir}")

    os.makedirs(generated_files_dir, exist_ok=True)

    # Change to project root directory before running OpenAPI generator
    original_dir = os.getcwd()
    os.chdir(project_root)
    
    result = subprocess.run([
        "java", "-jar", os.path.join(os.getcwd(), "bin", "openapi-generator-cli.jar"), "generate",
        "-i", os.path.join("bin", temp_json),
        "-g", "kotlin",
        "-o", ".",
        "-c", "openapi-config/openapi-config.yml",
        "--skip-validate-spec",
        "--global-property",
        "models,apis,supportingFiles=Utils.kt:src/main/kotlin/com/tidal/sdk/tidalapi/generated/ApiClient.kt"
    ])
    
    # Change back to original directory
    os.chdir(original_dir)

    if result.returncode != 0:
        logging.error("Error: Failed to generate files.")
        sys.exit(result.returncode)

    result = subprocess.run(
        [f"{sdk_root}/static-analysis/run-ktlint.sh", "-F", "-g", "-d", f"{project_root}/src"])

    if result.returncode != 0:
        logging.error("ktlint failed, but continuing with the rest of the script.")

    target_line = "import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*"
    remove_specific_line_from_files(f"{project_root}/src", target_line)

    logging.info("Generation complete and cleaned up.")

if __name__ == "__main__":
    main()
