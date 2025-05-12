#!/bin/bash

ROOT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && cd .. && pwd)
readonly ROOT_DIR
readonly CONFIG_DIR="$ROOT_DIR/static-analysis/config"
KTFMT_VERSION=$(<"$CONFIG_DIR/ktfmt-cli-version.txt")
readonly INSTALL_DIR="$ROOT_DIR/static-analysis/bin"
readonly INSTALL_TARGET="$INSTALL_DIR/ktfmt-$KTFMT_VERSION"

set -e
echo
echo "Running ktfmt version $KTFMT_VERSION"

exists() {
  command -v "$1" >/dev/null 2>&1
}

getKtfmt() {
  local expected_jar="ktfmt-$KTFMT_VERSION-jar-with-dependencies.jar"
  echo "Downloading ktfmt version $KTFMT_VERSION..."
  mkdir -p "$INSTALL_DIR"
  curl -sSLO "https://github.com/facebook/ktfmt/releases/download/v$KTFMT_VERSION/$expected_jar"
  mv "$expected_jar" "$INSTALL_TARGET"
  command="$INSTALL_TARGET"
}

# Initialize options
target_dir="$ROOT_DIR"
dry_run=false
input=()

# Analyze inputs
while getopts ":e:i:d:l" opt; do
  case $opt in
  e)
    command="$OPTARG"
    ;;
  i)
    input+=($OPTARG)
    ;;
  d)
    target_dir="$OPTARG" # Set target directory
    ;;
  l)
    dry_run=true # Lint mode (check only)
    ;;
  \?)
    echo "Invalid option -$OPTARG" >&2
    exit 1
    ;;
  esac

  case $OPTARG in
  -*)
    echo "Option $opt needs a valid argument"
    exit 1
    ;;
  esac
done

# Looking for ktfmt
if exists "$command"; then
  echo "ktfmt found at submitted location."
elif [ -s "$INSTALL_TARGET" ]; then
  command="$INSTALL_TARGET"
  echo "ktfmt version $KTFMT_VERSION found in $INSTALL_TARGET."
else
  echo "ktfmt version $KTFMT_VERSION not found. Installing into $INSTALL_DIR/..."
  getKtfmt
fi

# Specify which files to analyze
declare -a files_to_process

# Process input files if provided
for i in "${input[@]}"; do
  files_to_process+=("$i")
done

# If no input files, find files in target directory
if [ ${#files_to_process[@]} -eq 0 ]; then
  echo "No input files specified. Analyzing all Kotlin files in $target_dir..."
  while IFS= read -r -d $'\0' file; do
    files_to_process+=("$file")
  done < <(find "$target_dir" -type f \( -name "*.kt" -o -name "*.kts" \) -not -path "*/build/*" -not -path "*/.gradle/*" -print0)
fi

if [ "$dry_run" = true ]; then
  echo "Running ktfmt in lint mode (check only)..."
  java -jar "$command" --kotlinlang-style --dry-run --set-exit-if-changed "${files_to_process[@]}"
  exit_code=$?
  if [ $exit_code -ne 0 ]; then
    echo "ktfmt found formatting issues"
    exit $exit_code
  fi
else
  echo "Running ktfmt with Kotlin style (4-space indentation)..."
  java -jar "$command" --kotlinlang-style "${files_to_process[@]}"
fi
echo "Done."
