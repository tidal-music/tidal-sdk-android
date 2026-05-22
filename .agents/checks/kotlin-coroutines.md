---
name: kotlin-coroutines
description: Flag runBlocking in suspend, swallowed CancellationException, Thread.sleep in coroutines, withContext(SupervisorJob()).
severity-default: high
tools: [Bash, Read, Grep]
---

## Purpose

General (non-Android-scope) coroutine correctness. These patterns lead to deadlocks,
silent cancellation bugs, and structured-concurrency violations. Detekt does not catch
any of them.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` production files. **Skip test sources** (paths containing
   `/src/test/`, `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or
   `/src/sharedTest/`) — `android-testing-coroutines` covers those. If none remain,
   emit nothing and exit.
3. Scan added lines for `runBlocking`, `Thread.sleep`, `catch (e: Exception)` inside
   coroutine bodies, and `withContext(SupervisorJob())`.

## What to flag

- `runBlocking` inside a `suspend` function. Always wrong: block a coroutine thread to
  run another coroutine.
  ```kotlin
  // bad
  suspend fun loadAll(): List<Item> = runBlocking { repo.fetch() }
  // good
  suspend fun loadAll(): List<Item> = repo.fetch()
  ```
- `Thread.sleep` inside a coroutine — blocks the dispatcher thread.
  ```kotlin
  // bad
  suspend fun retry() { Thread.sleep(1_000); attempt() }
  // good
  suspend fun retry() { delay(1_000); attempt() }
  ```
- Swallowing `CancellationException` in a coroutine `try`/`catch`. Cancellation must
  propagate or the parent scope cannot cancel children.
  ```kotlin
  // bad
  try { fetch() } catch (e: Exception) { logger.w(e); null }
  // good
  try {
      fetch()
  } catch (e: CancellationException) {
      throw e
  } catch (e: Exception) {
      logger.w(e); null
  }
  ```
- `withContext(SupervisorJob())` — almost always a structured-concurrency mistake; the
  new `SupervisorJob` detaches from the parent so cancellation no longer propagates.
  Flag for review and ask the author what behavior they actually wanted: if they need a
  supervisor scope, `supervisorScope { ... }` is the right tool; if they just want to
  switch dispatchers, drop the `SupervisorJob()`.
  ```kotlin
  // flag — almost always wrong, but ask the author
  withContext(Dispatchers.IO + SupervisorJob()) { work() }
  // typical fix
  withContext(Dispatchers.IO) { work() }
  // for actual supervisor semantics
  supervisorScope { launch { work() } }
  ```

## What NOT to flag

- Existing `runBlocking` at synchronous-callback framework boundaries (e.g., OkHttp's
  `Authenticator.authenticate`, library `init` blocks, JNI bridges). The framework
  contract is synchronous; bridging via `runBlocking` is the established pattern.
  **New** instances still need human approval per `android-coroutine-scopes`.
- Anything inside test sources — defer to `android-testing-coroutines`, which understands
  `runTest` / `MainDispatcherRule` / dispatcher-sharing semantics that don't apply in
  production code.
- Existing direct `Dispatchers.IO` usage. **For new code only**, prefer the injected
  `com.tidal.android.core.coroutine.Dispatchers` abstraction from `:core:coroutine:main`
  when one is already wired through DI; do not retrofit the injected dispatcher into
  existing files unless you're already touching them for another reason.

## Output

```
<file>:<line> — runBlocking inside suspend function; remove the wrapper.
    bad:  suspend fun loadAll(): List<Item> = runBlocking { repo.fetch() }
    fix:  suspend fun loadAll(): List<Item> = repo.fetch()
```
