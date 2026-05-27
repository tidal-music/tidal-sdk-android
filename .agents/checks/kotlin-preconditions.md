---
name: kotlin-preconditions
description: Prefer require/check/error over manual throw IllegalArgumentException/IllegalStateException.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Kotlin's stdlib precondition helpers (`require`, `check`, `error`, `requireNotNull`,
`checkNotNull`) convey intent more clearly than a raw `throw` and produce consistent
exception messages. They also contract-check arguments at call sites, which helps the
compiler's flow analysis narrow types after the call.

## Instructions

1. Determine the merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` production files. **Skip test sources** — paths containing
   `/src/test/`, `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or
   `/src/sharedTest/`. Tests legitimately throw `IllegalArgumentException` /
   `IllegalStateException` to simulate failure paths or stub out collaborators;
   `require`/`check`/`error` discipline is a production-code concern. If none
   remain, emit nothing and exit.
3. Scan added lines for `throw IllegalArgumentException(`, `throw IllegalStateException(`,
   and `if (... == null) throw ...` forms.

## What to flag

- Manual `throw IllegalArgumentException` for argument validation.
  ```kotlin
  // bad
  if (id < 0) throw IllegalArgumentException("id must be non-negative")
  // good
  require(id >= 0) { "id must be non-negative" }
  ```
- Manual `throw IllegalStateException` for invariant checks.
  ```kotlin
  // bad
  if (user == null) throw IllegalStateException("user not loaded")
  // good
  checkNotNull(user) { "user not loaded" }
  ```
- Unconditional `throw IllegalStateException` at an unreachable branch — prefer `error(...)`.
  ```kotlin
  // bad
  throw IllegalStateException("unreachable")
  // good
  error("unreachable")
  ```

## What NOT to flag

- Domain-specific exception types (`throw AuthExpiredException(...)`) — those convey
  business meaning and should stay.
- `throw` inside a test's `assertFailsWith` setup.
- Exceptions wrapped for a third-party API contract (`throw IOException(cause)`).

## Output

```
<file>:<line> — Manual precondition throw; prefer stdlib helper.
    bad:  if (id < 0) throw IllegalArgumentException("id must be non-negative")
    fix:  require(id >= 0) { "id must be non-negative" }
```
