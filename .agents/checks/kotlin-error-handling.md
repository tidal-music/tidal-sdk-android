---
name: kotlin-error-handling
description: Flag empty catch blocks and exceptions used for control flow; prefer Result or sealed error types.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Empty `catch` blocks silently hide bugs. Exceptions used as control flow (return-via-throw,
catch-and-map-to-null) obscure the failure mode and prevent callers from handling specific
cases. Prefer `Result<T>`, `sealed` error types, or explicit nullable returns so failure
modes are visible in the signature. Detekt's `SwallowedException` rule already catches the
test-harness variant; this check focuses on structural "exceptions as control flow" smells
in production code.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` production files. **Skip test sources** — paths containing
   `/src/test/`, `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or
   `/src/sharedTest/`. If none remain, emit nothing and exit.
3. Scan added lines for:
   - Empty `catch { }` blocks (no body, or body that only swallows the exception).
   - `try { ... } catch (e: <SomeException>) { null }` / `... { return null }` /
     `... { defaultValue }` — i.e. any catch whose entire body discards the exception
     and returns a sentinel. This pattern occurs with `_`, named vars (`e`, `t`,
     `ex`, …) and any specific exception type (`NumberFormatException`,
     `IllegalArgumentException`, `IOException`, …), not just `catch (_: Exception)`.

## What to flag

- Empty `catch` that drops the exception entirely.
  ```kotlin
  // bad
  try {
      loadUser()
  } catch (e: Exception) {
  }
  // good
  runCatching { loadUser() }
      .onFailure { logger.w(it, "loadUser failed") }
  ```
- Exception as a control-flow return in a non-leaf function.
  ```kotlin
  // bad
  fun parseIdOrNull(raw: String): Int? =
      try { raw.toInt() } catch (e: NumberFormatException) { null }
  // good
  fun parseIdOrNull(raw: String): Int? = raw.toIntOrNull()
  ```
- Swallowing `CancellationException` inside a coroutine `try/catch`. Cross-reference
  `kotlin-coroutines` — the canonical fix is `catch (e: Exception) { if (e is CancellationException) throw e ... }`
  or a more specific catch.

## What NOT to flag

- `catch (e: Exception) { logger.e(e, ...) }` — the exception is reported, not swallowed.
- Framework boundary catches (OkHttp interceptors, WorkManager workers) where returning
  the framework's failure value is the correct behaviour.
- Retry loops that catch-and-continue on a specific transient exception type.

## Output

```
<file>:<line> — Empty catch block silently swallows exception.
    bad:  try { loadUser() } catch (e: Exception) { }
    fix:  runCatching { loadUser() }.onFailure { logger.w(it, "loadUser failed") }
```
