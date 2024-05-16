#!/bin/bash
set -eu

print_usage()
{
  echo "Usage: $0 -b <bom_module.json> -m <module_name>"
}

while getopts ":b:m:" OPT; do
  case $OPT in
    b) BOM_MODULE_FILE="$OPTARG"
    ;;
    m) MODULE_NAME="$OPTARG"
    ;;
    ?) print_usage
       exit 1
    ;;
  esac
done
if [ -z "${BOM_MODULE_FILE+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${MODULE_NAME+x}" ]; then
  print_usage
  exit 1
fi

./gradlew "$MODULE_NAME":generateMetadataFileForMavenPublication > /dev/null 2>&1

MODULE_FILE=$(./gradlew "$MODULE_NAME":properties | grep "^buildDir: " | awk '{print $2}')/publications/maven/module.json
MODULE_GROUP=$(jq -r ".component.group" "$MODULE_FILE")
MODULE_ARTIFACT=$(jq -r ".component.module" "$MODULE_FILE")

jq \
--arg MODULE_GROUP "$MODULE_GROUP" \
--arg MODULE_ARTIFACT "$MODULE_ARTIFACT" \
-r \
'.variants[0] | .dependencyConstraints[] | select(.group == $MODULE_GROUP) | select(.module == $MODULE_ARTIFACT) | .version.requires' "$BOM_MODULE_FILE"
