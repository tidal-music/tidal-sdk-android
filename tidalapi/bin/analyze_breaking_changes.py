#!/usr/bin/env python3
"""
Analyze Kotlin API changes to detect potential breaking changes.

This script compares two directories containing generated Kotlin API files
and determines if there are any breaking changes that would require a minor
version bump vs non-breaking changes that only need a patch version bump.

Usage:
    python3 analyze_breaking_changes.py <old_dir> <new_dir>

Returns:
    - Prints "BREAKING" if breaking changes detected
    - Prints "NON_BREAKING" if only non-breaking changes detected
    - Exit code 0 for success, 1 for errors
"""

import os
import re
import sys
import argparse
from pathlib import Path


def analyze_kotlin_changes(old_dir, new_dir):
    """
    Analyze Kotlin file changes to detect potential breaking changes.
    
    Args:
        old_dir: Path to directory with old/previous Kotlin files
        new_dir: Path to directory with new/updated Kotlin files
        
    Returns:
        tuple: (is_breaking: bool, changes: list of str)
    """
    breaking_patterns = [
        r'fun\s+\w+\s*\([^)]*\)\s*:\s*\w+',  # Function signature changes
        r'class\s+\w+',  # Class definition changes
        r'interface\s+\w+',  # Interface definition changes
        r'enum\s+class\s+\w+',  # Enum changes
        r'data\s+class\s+\w+',  # Data class changes
        r'@\w+',  # Annotation changes
    ]

    breaking_changes = []

    # Walk through all Kotlin files in the new directory
    for root, dirs, files in os.walk(new_dir):
        for file in files:
            if file.endswith('.kt'):
                new_file_path = os.path.join(root, file)
                old_file_path = new_file_path.replace(new_dir, old_dir)

                if os.path.exists(old_file_path):
                    # Compare existing files for changes
                    try:
                        with open(old_file_path, 'r', encoding='utf-8') as f:
                            old_content = f.read()
                        with open(new_file_path, 'r', encoding='utf-8') as f:
                            new_content = f.read()

                        if old_content != new_content:
                            # Check for breaking changes using pattern matching
                            for pattern in breaking_patterns:
                                old_matches = set(re.findall(pattern, old_content))
                                new_matches = set(re.findall(pattern, new_content))

                                # If signatures were removed or changed, it's potentially breaking
                                if old_matches != new_matches:
                                    removed = old_matches - new_matches
                                    added = new_matches - old_matches
                                    if removed:
                                        relative_path = os.path.relpath(new_file_path, new_dir)
                                        breaking_changes.append(
                                            f"Removed/changed in {relative_path}: {removed}")

                    except Exception as e:
                        print(f"Warning: Could not read file {file}: {e}", file=sys.stderr)
                        continue
                else:
                    # New file - generally non-breaking (addition)
                    continue

    # Check for deleted files (always breaking)
    for root, dirs, files in os.walk(old_dir):
        for file in files:
            if file.endswith('.kt'):
                old_file_path = os.path.join(root, file)
                new_file_path = old_file_path.replace(old_dir, new_dir)

                if not os.path.exists(new_file_path):
                    relative_path = os.path.relpath(old_file_path, old_dir)
                    breaking_changes.append(f"Deleted file: {relative_path}")

    return len(breaking_changes) > 0, breaking_changes


def main():
    parser = argparse.ArgumentParser(
        description="Analyze Kotlin API changes for breaking changes",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
    # Compare two generated API directories
    python3 analyze_breaking_changes.py /path/to/old/generated /path/to/new/generated
    
    # Use with relative paths
    python3 analyze_breaking_changes.py ../backup/generated src/main/kotlin/.../generated
        """
    )
    parser.add_argument('old_dir', help='Directory containing old/previous Kotlin files')
    parser.add_argument('new_dir', help='Directory containing new/updated Kotlin files')
    parser.add_argument('-v', '--verbose', action='store_true',
                        help='Show detailed information about detected changes')

    args = parser.parse_args()

    # Validate directories exist
    if not os.path.isdir(args.old_dir):
        print(f"Error: Old directory '{args.old_dir}' does not exist", file=sys.stderr)
        return 1

    if not os.path.isdir(args.new_dir):
        print(f"Error: New directory '{args.new_dir}' does not exist", file=sys.stderr)
        return 1

    # Analyze changes
    is_breaking, changes = analyze_kotlin_changes(args.old_dir, args.new_dir)

    if is_breaking:
        print("BREAKING")
        if args.verbose or len(sys.argv) == 1:  # Show details if verbose or if run without args
            for change in changes:
                print(f"  {change}")
    else:
        print("NON_BREAKING")
        if args.verbose:
            print("  No breaking changes detected")

    return 0


if __name__ == "__main__":
    sys.exit(main())
