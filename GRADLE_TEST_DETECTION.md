# Gradle-Based Affected Module Detection

This document describes the Gradle-based approach for detecting which modules are affected by code changes and selectively running their tests.

## Overview

Instead of using a complex bash script to determine which modules are affected by changes, we now use a Gradle task that leverages Gradle's built-in understanding of project dependencies. This ensures that the dependency detection is always accurate and in sync with the actual build configuration.

## How It Works

### 1. The Gradle Convention Plugin

The `detectAffectedModules` task is provided by the `AffectedTestDetectionPlugin` in the `buildlogic` module. This follows Gradle best practices by implementing build logic as a convention plugin rather than directly in the root build file.

The plugin (`buildlogic/src/main/kotlin/com/tidal/sdk/plugins/AffectedTestDetectionPlugin.kt`):

- Detects changed files using `git diff`
- Maps files to their containing Gradle projects
- Automatically includes all projects that depend on affected projects
- Handles transitive dependencies correctly
- Outputs a space-separated list of test tasks to run

### 2. Usage

#### Local Development

Run the task locally to see which modules would be tested:

```bash
# Compare against origin/main (default)
./gradlew detectAffectedModules

# Compare against a specific ref
./gradlew detectAffectedModules -PbaseRef=HEAD~1

# Force all tests
./gradlew detectAffectedModules -PrunAllTests=true
```

The task outputs:
- To stdout: Space-separated list of test tasks (for CI consumption)
- To `build/affected-tests.txt`: The same list for debugging
- To console: Detailed logging about the detection process

#### Running Tests Locally

To run tests for only the affected modules:

```bash
# Detect and run affected tests
TASKS=$(./gradlew detectAffectedModules --quiet)
if [ -n "$TASKS" ]; then
  ./gradlew $TASKS
fi
```

### 3. GitHub Actions Integration

The workflow in `.github/workflows/unit-test.yml` uses the Gradle task:

1. Determines the base ref (PR base branch or HEAD~1)
2. Runs `detectAffectedModules` to get the list of test tasks
3. Either runs selective tests or all tests based on the output

## Advantages Over Bash Script

### 1. **Always Accurate**
- Uses Gradle's actual dependency graph
- No manual maintenance of module dependencies
- Automatically stays in sync with build configuration

### 2. **Handles Transitive Dependencies**
- Correctly identifies all affected modules through the dependency chain
- Example: Changing `common` correctly triggers tests for `auth`, `player`, etc.

### 3. **Testable Locally**
- Same behavior locally and in CI
- Easy to debug and verify
- Can be tested without pushing changes

### 4. **Maintainable**
- Written in Kotlin (type-safe, IDE support)
- Uses Gradle's APIs directly
- No complex string manipulation or bash arrays

### 5. **Extensible**
- Easy to add custom logic for specific modules
- Can integrate with other Gradle plugins
- Could be extended to handle other tasks beyond testing

## Module Detection Rules

### Root-Level Changes
The following changes trigger ALL tests:
- `build.gradle.kts`
- `settings.gradle.kts`
- `gradle.properties`
- `gradlew`, `gradlew.bat`
- Files in `gradle/` directory
- Files in `buildlogic/` directory
- Files in `.github/workflows/` directory

### Module-Specific Changes
- Files within a module directory trigger that module's tests
- All modules that depend on the changed module are also tested
- Transitive dependencies are included

### Excluded Modules
These modules don't have test tasks:
- `buildlogic`
- `template`
- `bom`

## Troubleshooting

### No tests running when expected

Check the output of:
```bash
./gradlew detectAffectedModules --info
```

This will show:
- Which files were detected as changed
- Which projects were mapped to those files
- The dependency resolution process

### All tests running unexpectedly

This happens when:
- Root-level files are changed (see list above)
- The Git diff fails (fallback to all tests)
- No base ref can be determined

### Debugging

1. Check the generated file:
```bash
cat build/affected-tests.txt
```

2. Run with more logging:
```bash
./gradlew detectAffectedModules --info
```

3. Verify Git diff manually:
```bash
git diff --name-only origin/main...HEAD
```

## Migration from Bash Script

The old bash script (`detect-affected-modules.sh`) is no longer needed. The Gradle task provides all the same functionality with better accuracy and maintainability.

Key differences:
- No need to manually maintain module dependencies
- Handles transitive dependencies correctly
- Simpler CI integration
- Works identically in local and CI environments