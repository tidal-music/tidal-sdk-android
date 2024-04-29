#!/bin/bash
readonly PLACEHOLDER="template"
readonly ROOT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

check_correct_repo(){
    if ! find $ROOT_DIR -maxdepth 1 -type d | grep -q $PLACEHOLDER; then
        echo "Repository root does not contain a '$PLACEHOLDER' directory!"
        echo "Are you sure you are in the correct project for this script?"
        exit 1
    fi
}

uppercase(){
    string=$1
    first=`echo $string|cut -c1|tr [a-z] [A-Z]`
    second=`echo $string|cut -c2-`
    echo $first$second
}

check_correct_repo

printf "\nEnter new module's name, using capitalized CamelCase\nExample: PlaybackEngine\n"
read module_name 

printf "\nDo you want to create a module named '%s'?" "$module_name"

read -r -p "(y/n)"
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Aborted!"     
    exit 1
fi

module_name_lowercase=$(echo "${module_name}" | tr '[:upper:]' '[:lower:]')

echo "Copying files to '$ROOT_DIR/$module_name_lowercase'..."
mkdir -p $ROOT_DIR/$module_name_lowercase
rsync -a $ROOT_DIR/$PLACEHOLDER/* $ROOT_DIR/$module_name

echo  "Rename directories to '$module_name_lowercase'..."
for file in $(find "$ROOT_DIR/$module_name_lowercase" -name "*$PLACEHOLDER*" -type d -depth); do
    if [[ ! $file =~ "build" ]]; then 
        dirname=$(echo $file)
        fixed=$(echo $dirname | sed -r "s/(.*)$PLACEHOLDER/\1$module_name_lowercase/")
        mv $file $fixed
    fi    
done   

echo "Rename keywords in files to '$module_name'..."
for file in $(find "$ROOT_DIR/$module_name_lowercase" -type f); do
        if [[ $file == *.kt || $file == *.gradle*kts || $file == *.xml || $file == *.md ]]; then
            sed -i '' "s/$PLACEHOLDER/$module_name_lowercase/g" $file
            sed -i '' "s/$(uppercase $PLACEHOLDER)/$module_name/g" $file
        fi
done

echo "Rename keywords in file name_inputs to '$module_name_lowercase'..."
for file in $(find "$ROOT_DIR/$module_name_lowercase" -name "*$(uppercase $PLACEHOLDER)*" -type f); do
    if [[ $file == *.kt || $file == *.kts || $file == *.xml ]]; then
        filename=$(echo $file)
        fixed=$(echo $filename | sed "s/$PLACEHOLDER/$module_name_lowercase/g")
        fixed=$(echo $filename | sed "s/$(uppercase $PLACEHOLDER)/$module_name/g")
        mv $file $fixed
    fi
done

echo "includeFromDefaultHierarchy(\"$module_name_lowercase\")" >> settings.gradle.kts

echo "Done! Module '$module_name' has been successfully created in '$ROOT_DIR/$module_name_lowercase'."
