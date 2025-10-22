# Affected Modules Detection Scripts

This directory contains scripts for detecting affected modules based on Git changes and running targeted tests.

## Scripts

### `detect-affected-modules.sh`

The main script that detects which modules are affected by changes in a Git commit or PR and outputs the corresponding Gradle test tasks.

#### Usage

```bash
# Use default base commit (origin/main)
./detect-affected-modules.sh

# Compare against a specific commit/branch
./detect-affected-modules.sh origin/develop
./detect-affected-modules.sh HEAD~1
./detect-affected-modules.sh abc123

# Show help
./detect-affected-modules.sh --help
```

#### Output

The script outputs a space-separated list of Gradle test tasks to run:

```bash
:auth:test :eventproducer:test :player:test
```

#### Features

1. **File-to-Module Mapping**: Maps changed files to their corresponding modules based on directory structure
2. **Dependency Analysis**: Identifies dependent modules that need testing when a dependency changes
3. **Root-level Change Detection**: Detects changes to build files, Gradle configuration, or CI workflows that require running all tests
4. **Edge Case Handling**: Handles various scenarios like renamed files, new files, and deleted files
5. **Robust Error Handling**: Provides clear error messages and handles Git repository issues

#### Module Detection Logic

- **Direct Changes**: Files changed in a module directory directly affect that module
- **Dependency Changes**: When a module changes, all modules that depend on it are also affected
- **Root Changes**: Changes to root-level build files, Gradle configuration, or CI workflows trigger all tests

#### Root-level Changes (Run All Tests)

The following changes trigger all tests:
- `build.gradle.kts`
- `settings.gradle.kts` 
- `gradle.properties`
- `gradlew` / `gradlew.bat`
- Files in `gradle/` directory
- Files in `buildlogic/` directory
- Files in `.github/workflows/` directory

### `test-detect-affected-modules.sh`

A test script that validates the functionality of `detect-affected-modules.sh` with various scenarios.

#### Usage

```bash
./test-detect-affected-modules.sh
```

## Module Structure

The script understands the following module structure:

```
├── auth/                    # Authentication module
├── bom/                     # Bill of Materials
├── buildlogic/              # Build logic (affects all modules)
├── common/                  # Common utilities (dependency for many modules)
├── eventproducer/           # Event producer (depends on auth, common)
├── player/                  # Player module (depends on auth, eventproducer, common)
│   ├── common/              # Player common utilities
│   ├── common-android/      # Player Android utilities
│   ├── events/              # Player events
│   ├── playback-engine/     # Playback engine
│   ├── streaming-api/       # Streaming API
│   ├── streaming-privileges/# Streaming privileges
│   └── testutil/            # Test utilities
├── tidalapi/                # Tidal API client
└── template/                # Module template
```

## Dependency Graph

```
common
├── auth (depends on common)
│   └── eventproducer (depends on auth, common)
│       └── player (depends on eventproducer, auth, common)
└── tidalapi (depends on common)
```

## Integration with CI/CD

### GitHub Actions

The script is designed to work with GitHub Actions. See `.github/workflows/test-affected-modules.yml` for an example workflow that:

1. Detects affected modules on PR or push
2. Runs only the necessary tests
3. Handles the case where all tests need to run
4. Uploads test results as artifacts

### Local Development

Developers can use the script locally to run only affected tests:

```bash
# Test changes since last commit
./gradlew $(.github/scripts/detect-affected-modules.sh HEAD~1)

# Test changes in current branch vs main
./gradlew $(.github/scripts/detect-affected-modules.sh origin/main)
```

## Customization

To add new modules or modify dependencies:

1. Edit the `get_module_info()` function in `detect-affected-modules.sh`
2. Add entries in the format: `module_name:gradle_path:directory_path:dependencies`
3. Dependencies should be comma-separated (e.g., `common,auth`)

Example:
```bash
newmodule:newmodule:newmodule/:common,auth
```

## Troubleshooting

### Common Issues

1. **"Not in a Git repository"**: Ensure you're running the script from within the Git repository
2. **"Base commit does not exist"**: The specified base commit/branch doesn't exist. Check your Git history
3. **No modules detected**: The changed files don't match any known module directories

### Debug Mode

For debugging, you can examine the script's log output (sent to stderr):

```bash
./detect-affected-modules.sh 2>&1 | grep INFO
```

### Manual Testing

You can test the script by making changes to files and running it:

```bash
# Make a change
echo "# test" >> auth/README.md

# Test the script
./detect-affected-modules.sh HEAD

# Clean up
git checkout -- auth/README.md
```
