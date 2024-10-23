#!/bin/bash

ROOT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )
readonly ROOT_DIR
readonly CONFIG_DIR="$ROOT_DIR/static-analysis/config"
KTLINT_VERSION=$(<"$CONFIG_DIR/ktlint-cli-version.txt")
readonly KTLINT_VERSION
readonly INSTALL_DIR="$ROOT_DIR/static-analysis/bin"
readonly INSTALL_TARGET="$INSTALL_DIR/ktlint"

set -e
echo
echo "Running ktlint version $KTLINT_VERSION"

exists(){
  command -v "$1" >/dev/null 2>&1
}

getKtlint(){
  rm "$INSTALL_TARGET" 2> /dev/null || true
  mkdir -p "$INSTALL_DIR"
  curl -sSLO -v "https://github.com/pinterest/ktlint/releases/download/$KTLINT_VERSION/ktlint"
  mv ktlint "$INSTALL_TARGET"
  chmod a+x "$INSTALL_TARGET"
  command="$INSTALL_TARGET"
}

# Initialize options
auto_fix_flag=""
scan_generated_files=false
target_dir="$ROOT_DIR"
input=()

# Analyze inputs
while getopts ":e:i:Fd:g" opt; do
  case $opt in
    e) command="$OPTARG"
      ;;
    i) input+=($OPTARG)
      ;;
    F) auto_fix_flag="-F" # Enable auto-fix
      ;;
    d) target_dir="$OPTARG" # Set target directory
      ;;
    g) scan_generated_files=true # Include generated files
      ;;
    \?) echo "Invalid option -$OPTARG" >&2
        exit 1
      ;;
  esac

  case $OPTARG in
    -*) echo "Option $opt needs a valid argument"
        exit 1
      ;;
  esac
done

# Looking for ktlint
if exists "$command"; then
  echo "ktlint found at submitted location."
elif exists "$INSTALL_TARGET"; then
  command="$INSTALL_TARGET"
  echo "ktlint found in $INSTALL_TARGET."

  # Safeguard correct version
  installed_version="$($command --version)"
  if [ "$installed_version" != "$KTLINT_VERSION" ]; then
    echo "ktlint version $installed_version outdated! Updating to $KTLINT_VERSION..."
    getKtlint
  fi
else
  echo "ktlint not found. Installing into $INSTALL_DIR/..."
  getKtlint
fi

# Specify which files to analyze
files_argument=""
for i in "${input[@]}"; do
  if [ "$scan_generated_files" = false ] && [[ "$i" == *'/generated/'* ]]; then
    echo "Ignoring generated file: $i"
  else
    files_argument="$files_argument\"./$i\" "
  fi
done

# Set files to analyze based on whether input files or directory were provided
if [ "$files_argument" == "" ]; then
  echo "No input files specified. Analyzing all Kotlin files in $target_dir..."
  if [ "$scan_generated_files" = true ]; then
    files_argument="$target_dir/**/*.kt $target_dir/**/*.kts !**/build/** !**/*.mustache"
  else
    files_argument="$target_dir/**/*.kt $target_dir/**/*.kts !**/build/** !**/generated/** !**/*.mustache"
  fi
fi

echo $files_argument
echo "Running ktlint..."
command="$command $files_argument --experimental --reporter plain --baseline=$CONFIG_DIR/ktlint-baseline.xml $auto_fix_flag"
eval "$command"
echo "Done."
