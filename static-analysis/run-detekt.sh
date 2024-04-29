#!/bin/bash
ROOT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )
readonly ROOT_DIR
readonly CONFIG_DIR="$ROOT_DIR/static-analysis/config"
DETEKT_VERSION=$(<"$CONFIG_DIR/detekt-cli-version.txt")
readonly DETEKT_VERSION
readonly INSTALL_DIR="$ROOT_DIR/static-analysis/bin/detekt"
readonly DETEKT_CONFIG="$CONFIG_DIR/detekt-rules.yml"

exists(){
  command -v "$1" >/dev/null 2>&1
}

getDetekt(){
  rm -R "$INSTALL_DIR" 2> /dev/null || true
  mkdir -p "$INSTALL_DIR"
  curl -sSLO -v "https://github.com/detekt/detekt/releases/download/v$DETEKT_VERSION/detekt-cli-$DETEKT_VERSION.zip"
  unzip "detekt-cli-$DETEKT_VERSION.zip" -d "$INSTALL_DIR"
  rm "detekt-cli-$DETEKT_VERSION.zip"
  command="$INSTALL_DIR/detekt-cli-$DETEKT_VERSION/bin/detekt-cli"
}

# Analyze input arguments
input=()
while getopts ":e:i:" opt; do
  case $opt in
    e) command="$OPTARG"
      ;;
    i) input+=($OPTARG);
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

set -e
echo "Using Detekt version $DETEKT_VERSION"

# Look for Detekt
if exists "$command"; then
  echo "Detekt found at submitted location."
elif exists "$INSTALL_DIR/detekt-cli-$DETEKT_VERSION/bin/detekt-cli"; then
  echo "Detekt found in $INSTALL_DIR."
  command="$INSTALL_DIR/detekt-cli-$DETEKT_VERSION/bin/detekt-cli"
else
  echo "Detekt not found. Installing..."
  getDetekt
fi


# Specify which files to analyze
files_argument=""
for i in "${input[@]}"; do
  files_argument="$files_argument$i,"
done

if [ -n "$files_argument" ]; then
  files_argument="--input ${files_argument%?}"
fi

echo
echo "Running Detekt..."
command="$command --config $DETEKT_CONFIG --build-upon-default-config --parallel --excludes '**/build/**' --auto-correct $files_argument "
eval "$command"
echo "Done."
