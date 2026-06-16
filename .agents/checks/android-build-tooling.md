---
name: android-build-tooling
description: Require new dependencies to go through gradle/libs.versions.toml; flag any new kapt usage when KSP is available.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

The SDK uses KSP for annotation processing (Dagger components, Room compilers, Moshi codegen). The primary job of this rule is **catalog enforcement**: version-catalog entries (`gradle/libs.versions.toml`) give one place to bump a dependency across the SDK's modules; inline `"group:artifact:version"` strings in a build file sidestep that. The secondary check is a tripwire for new `kapt` usage — KSP is materially faster and is the established convention here, so introducing kapt for a processor that has a KSP implementation splits the build pipeline.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. **Filter to build files.** Keep only `*.gradle.kts` and `*.gradle`. Skip
   `gradle/libs.versions.toml` — inline `"group:artifact:version"` strings
   belong in the catalog itself, so scanning the catalog would flag every
   correct entry. Also skip `buildlogic/**` convention-plugin code where
   coordinates may legitimately come from the catalog as `String` arguments.
3. If no matching files remain, emit nothing and exit.
4. Scan added lines for `kapt(...)`, `apply plugin: 'kotlin-kapt'` / `id("kotlin-kapt")`,
   and inline string-literal dependencies (`"group:artifact:version"`).

## What to flag

- New `kapt` usage where a KSP implementation exists. The SDK is on KSP everywhere it can be; introducing kapt elsewhere splits the build pipeline and slows incremental builds.
  ```kotlin
  // bad
  plugins { id("kotlin-kapt") }
  dependencies { kapt(libs.dagger.compiler) }
  // good
  plugins { id("com.google.devtools.ksp") }
  dependencies { ksp(libs.dagger.compiler) }
  ```
- Inline dependency coordinate instead of catalog reference.
  ```kotlin
  // bad
  dependencies { implementation("com.squareup.okhttp3:okhttp:4.12.0") }
  // good — add to libs.versions.toml, then:
  dependencies { implementation(libs.okhttp) }
  ```

## What NOT to flag

- `kapt` for a processor that has no KSP implementation yet (check the processor's docs
  before recommending a switch). When in doubt, ask in the PR.
- Composite-build plugin coordinates (`id("buildlogic.android-library")`) — those are
  project-local convention plugins and not catalog candidates.
- Direct `project(":auth")` or `project(":common")` module references — module
  dependencies don't go through the catalog.

## Output

```
<file>:<line> — Inline dependency coordinate; move to version catalog.
    bad:  implementation("com.squareup.okhttp3:okhttp:4.12.0")
    fix:  implementation(libs.okhttp)  // after adding `okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp3" }` to libs.versions.toml
```
