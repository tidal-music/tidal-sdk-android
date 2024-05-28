#!/bin/bash

create_git_hooks() {
    echo "Copying pre-commit hook" >&2
    mkdir -p "./.git/hooks/"
    cp "./static-analysis/git-hooks/pre-commit" "./.git/hooks/pre-commit"
    chmod a+x ".git/hooks/pre-commit"
    echo "Done." >&2
}

create_git_hooks
