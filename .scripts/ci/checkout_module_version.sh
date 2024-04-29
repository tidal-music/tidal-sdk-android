#!/bin/bash
set -eu

print_usage()
{
  echo "Usage: $0 -m <module_name> -v <git_ref> -s <support_clone_path>"
}

while getopts ":m:v:s:" OPT; do
  case $OPT in
    m) MODULE_NAME="$OPTARG"
    ;;
    v) VERSION_REF="$OPTARG"
    ;;
    s) PATH_CLONE_SUPPORT="$OPTARG"
    ;;
    ?) print_usage
       exit 1
    ;;
  esac
done
if [ -z "${MODULE_NAME+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${VERSION_REF+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${PATH_CLONE_SUPPORT+x}" ]; then
  print_usage
  exit 1
fi

WORKDIR=$(pwd)

rm -rf "$(./gradlew "$MODULE_NAME":properties | grep "^projectDir: " | awk '{print $2}')"
cd "$PATH_CLONE_SUPPORT"
git checkout "$VERSION_REF"
rsync -a "$(./gradlew "$MODULE_NAME":properties | grep "^projectDir: " | awk '{print $2}')" "$WORKDIR"
cd "$WORKDIR"
