---
name: kotlin-java-isms
description: Replace Java-style getters, util classes, and string concatenation with Kotlin properties, top-level funcs, and string templates.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

Kotlin code that reads like Java misses idioms that the language was built to improve.
Properties beat getter methods for zero-arg accessors, top-level functions beat `object`
util holders, and string templates beat concatenation for readability.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for **zero-argument** member functions matching
   `fun get[A-Z]\w*\(\)` or `fun is[A-Z]\w*\(\)` (predicates / getters that take a
   parameter are not property candidates and must not be flagged), `object XxxUtils`,
   and `"..." + var + "..."` concatenations.

## What to flag

- Java-style getter where a property fits.
  ```kotlin
  // bad
  class User {
      fun getFullName(): String = "$firstName $lastName"
  }
  // good
  class User {
      val fullName: String get() = "$firstName $lastName"
  }
  ```
- `object XxxUtils` holding only top-level-like functions.
  ```kotlin
  // bad
  object StringUtils {
      fun capitalize(s: String): String = s.replaceFirstChar { it.uppercase() }
  }
  // good — top-level extension in StringExtensions.kt
  fun String.capitalizeFirst(): String = replaceFirstChar { it.uppercase() }
  ```
- String concatenation where a template reads more clearly.
  ```kotlin
  // bad
  val msg = "Hello " + name + "! You have " + count + " messages."
  // good
  val msg = "Hello $name! You have $count messages."
  ```

## What NOT to flag

- Generated Kotlin (Dagger, Room, Anvil) — `getXxx` is compiler output.
- `fun getXxx(param: X)` or `fun isXxx(param: X)`: takes a parameter, so not a
  zero-arg property candidate. Predicate functions like
  `fun isPermissionGranted(permission: String): Boolean` are perfectly idiomatic.
- Java interop contract where a Java caller expects `getXxx()` — honor `@JvmName` / method
  name if present.
- Concatenation involving newlines or conditional formatting where a template would be
  unreadable.

## Output

```
<file>:<line> — Java-style getter; prefer Kotlin property.
    bad:  fun getFullName(): String = "$firstName $lastName"
    fix:  val fullName: String get() = "$firstName $lastName"
```
