import json
import logging
import os
import requests
import shutil
import subprocess
import sys


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


def download_api_spec(json_file_path):
    try:
        with open(json_file_path, 'r') as f:
            config = json.load(f)
    except Exception as e:
        logging.error(f"Failed to read JSON file {json_file_path}: {e}")
        sys.exit(1)

    input_url = config.get('input')
    if not input_url:
        logging.error("No input URL specified in the configuration.")
        sys.exit(1)

    output_path = config.get('output')
    if not output_path:
        logging.error("No output file specified in the configuration.")
        sys.exit(1)

    openapi_generator_path = config.get('openapi_generator_path')
    if not openapi_generator_path:
        logging.error("No openapi-generator-cli.jar path specified in the configuration.")
        sys.exit(1)

    temp_dir = os.path.join(os.getcwd(), 'openapi_downloads')
    os.makedirs(temp_dir, exist_ok=True)
    logging.info(f"Created directory {temp_dir}")

    try:
        downloaded_file = download_file(input_url, temp_dir)
        shutil.copy(downloaded_file, output_path)
        os.remove(downloaded_file)
        logging.info(f"API specification saved to {output_path}")
    except Exception as e:
        logging.error(f"Error downloading API specification: {e}")
        sys.exit(1)

    return output_path, openapi_generator_path


def main():
    setup_logging()

    project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
    sdk_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '../..'))

    if len(sys.argv) < 2:
        logging.info("Error: You must provide the path to the config file.")
        logging.info(f"Usage: {sys.argv[0]} <path to config.json>")
        sys.exit(1)

    config_file = sys.argv[1]

    # Download the API spec and get the path to the OpenAPI generator jar
    temp_json, source = download_api_spec(config_file)

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