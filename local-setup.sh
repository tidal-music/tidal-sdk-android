#!/bin/bash

create_git_hooks() {
    echo "Copying pre-commit hook" >&2
    mkdir -p "./.git/hooks/"
    cp "./static-analysis/git-hooks/pre-commit" "./.git/hooks/pre-commit"
    chmod a+x ".git/hooks/pre-commit"
    echo "Done." >&2
}

setup_git_blame_ignore() {
    echo ""
    echo "Would you like to configure Git to ignore marked commits when using git blame?"
    echo "Commits to be ignored are listed in '.git-blame-ignore-revs' (y/n)"
    read -r setup_blame_ignore

    if [[ "$setup_blame_ignore" =~ ^[Yy]$ ]]; then
        echo "Setting up Git blame ignore configuration..."
        git config blame.ignoreRevsFile .git-blame-ignore-revs
        echo "Git blame ignore configuration complete."
    else
        echo "Skipping Git blame ignore configuration."
    fi
}

create_git_hooks
setup_git_blame_ignore
