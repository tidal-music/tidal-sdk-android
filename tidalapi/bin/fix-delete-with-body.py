#!/usr/bin/env python3

import re
import sys
import os

def fix_delete_with_body(file_path):
    """
    Fix DELETE methods that have @Body parameters to use @HTTP annotation instead of @DELETE
    """
    
    with open(file_path, 'r') as f:
        content = f.read()
    
    # Pattern to find DELETE methods with @Body parameters
    # Match @DELETE annotation, then function signature that contains @Body parameter
    pattern = r'(@DELETE\("[^"]+"\))\s*\n\s*suspend fun ([^(]+)\((.*?@Body.*?)\): Response<'
    
    def replacement(match):
        delete_annotation = match.group(1)
        function_name = match.group(2).strip()
        params = match.group(3)
        
        # Extract the path from @DELETE("path")
        path_match = re.search(r'@DELETE\("([^"]+)"\)', delete_annotation)
        if path_match:
            path = path_match.group(1)
            return f'@HTTP(method = "DELETE", path = "{path}", hasBody = true)\n    suspend fun {function_name}(\n{params}\n    ): Response<'
        
        return match.group(0)  # Return original if we can't parse
    
    # Apply the replacement
    modified_content = re.sub(pattern, replacement, content, flags=re.MULTILINE | re.DOTALL)
    
    # Write back if there were changes
    if modified_content != content:
        with open(file_path, 'w') as f:
            f.write(modified_content)
        print(f"Fixed DELETE with body annotations in {file_path}")
        return True
    
    return False

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: python3 fix-delete-with-body.py <kotlin_file_path>")
        sys.exit(1)
    
    file_path = sys.argv[1]
    if not os.path.exists(file_path):
        print(f"File not found: {file_path}")
        sys.exit(1)
    
    fix_delete_with_body(file_path)