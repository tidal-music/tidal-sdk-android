import json
import logging
import os
import requests
import shutil
import subprocess
import sys
import argparse


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


def process_api_spec(json_file_path, local_file=None, custom_url=None):
    try:
        with open(json_file_path, 'r') as f:
            config = json.load(f)
    except Exception as e:
        logging.error(f"Failed to read JSON file {json_file_path}: {e}")
        sys.exit(1)

    # Get the OpenAPI generator path from config
    openapi_generator_path = config.get('openapi_generator_path')
    if not openapi_generator_path:
        logging.error("No openapi-generator-cli.jar path specified in the configuration.")
        sys.exit(1)

    output_path = config.get('output')
    if not output_path:
        logging.error("No output file specified in the configuration.")
        sys.exit(1)

    # Handle local file case
    if local_file:
        logging.info(f"Using local file: {local_file}")
        try:
            # Copy the local file to the output path
            shutil.copy2(local_file, output_path)
            logging.info(f"Copied local file to {output_path}")
            return output_path, openapi_generator_path
        except Exception as e:
            logging.error(f"Failed to copy local file: {e}")
            sys.exit(1)

    # Handle URL case (either custom or from config)
    input_url = custom_url if custom_url else config.get('input')
    if not input_url:
        logging.error("No input URL specified in the configuration or command line.")
        sys.exit(1)

    temp_dir = os.path.join(os.getcwd(), 'openapi_downloads')
    os.makedirs(temp_dir, exist_ok=True)
    logging.info(f"Created directory {temp_dir}")

    # Download the API spec
    try:
        logging.info(f"Downloading API spec from {input_url}")
        local_file = download_file(input_url, temp_dir)
        logging.info(f"Successfully downloaded API spec to {local_file}")
        shutil.copy2(local_file, output_path)
        logging.info(f"Copied API spec to {output_path}")
        return output_path, openapi_generator_path
    except Exception as e:
        logging.error(f"Failed to download API spec: {e}")
        sys.exit(1)


def main():
    setup_logging()

    # Parse command line arguments
    parser = argparse.ArgumentParser(description='Generate API files from OpenAPI specification')
    parser.add_argument('config_file', help='Path to the configuration JSON file')
    parser.add_argument('--local-file', help='Path to a local OpenAPI JSON file')
    parser.add_argument('--url', help='Custom URL to download the OpenAPI JSON file')
    args = parser.parse_args()

    project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
    sdk_root = os.path.abspath(os.path.join(os.path.dirname(__file__), '../..'))

    logging.info(f"Project root: {project_root}")
    logging.info(f"SDK root: {sdk_root}")
    logging.info(f"Using config file: {args.config_file}")

    # Process the API spec (download or use local file)
    temp_json, source = process_api_spec(args.config_file, args.local_file, args.url)
    logging.info(f"API spec processed: {temp_json}")
    logging.info(f"OpenAPI generator path: {source}")

    # Define the generated files path and clean the directory
    generated_files_dir = os.path.join(project_root,
                                     "src/main/kotlin/com/tidal/sdk/tidalapi/generated")

    # Clean the directory
    if os.path.exists(generated_files_dir):
        shutil.rmtree(generated_files_dir)
        logging.info(f"Cleaned up directory: {generated_files_dir}")

    os.makedirs(generated_files_dir, exist_ok=True)
    logging.info(f"Created generated files directory: {generated_files_dir}")

    # Change to project root directory before running OpenAPI generator
    original_dir = os.getcwd()
    os.chdir(project_root)
    logging.info(f"Changed working directory to: {os.getcwd()}")
    
    # Verify jar file exists
    jar_path = os.path.join(os.getcwd(), "bin", "openapi-generator-cli.jar")
    if not os.path.exists(jar_path):
        logging.error(f"OpenAPI generator JAR not found at: {jar_path}")
        sys.exit(1)
    logging.info(f"Found OpenAPI generator JAR at: {jar_path}")

    # Build the command
    cmd = [
        "java", "-jar", jar_path, "generate",
        "-i", os.path.join("bin", os.path.basename(temp_json)),
        "-g", "kotlin",
        "-o", ".",
        "-c", "openapi-config/openapi-config.yml",
        "--skip-validate-spec",
        "--global-property",
        "models,apis,supportingFiles=Utils.kt:src/main/kotlin/com/tidal/sdk/tidalapi/generated/ApiClient.kt"
    ]
    logging.info(f"Executing command: {' '.join(cmd)}")
    
    try:
        result = subprocess.run(cmd, capture_output=True, text=True)
        logging.info(f"OpenAPI generator stdout: {result.stdout}")
        if result.stderr:
            logging.error(f"OpenAPI generator stderr: {result.stderr}")
        
        if result.returncode != 0:
            logging.error(f"OpenAPI generator failed with return code: {result.returncode}")
            sys.exit(result.returncode)
    except Exception as e:
        logging.error(f"Failed to execute OpenAPI generator: {e}")
        sys.exit(1)
    
    # Change back to original directory
    os.chdir(original_dir)
    logging.info(f"Changed working directory back to: {os.getcwd()}")

    logging.info("Running ktfmt...")
    result = subprocess.run(
        [f"{sdk_root}/static-analysis/run-ktfmt.sh", "-d", f"{project_root}/src"],
        capture_output=True,
        text=True
    )

    if result.returncode != 0:
        logging.warning(f"ktfmt failed with return code {result.returncode}")
        logging.warning(f"ktfmt stdout: {result.stdout}")
        logging.warning(f"ktfmt stderr: {result.stderr}")
        logging.warning("Continuing with the rest of the script despite ktfmt failure.")

    target_line = "import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*"
    remove_specific_line_from_files(f"{project_root}/src", target_line)

    logging.info("Generation complete and cleaned up.")


if __name__ == "__main__":
    main()
