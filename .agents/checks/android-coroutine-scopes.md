---
name: android-coroutine-scopes
description: Flag GlobalScope, runBlocking on caller threads, fire-and-forget launches, and missing SupervisorJob in long-lived scopes.
severity-default: high
tools: [Bash, Read, Grep]
---

## Purpose

Library code is consumed by many host apps with their own threading models. `GlobalScope` leaks structured concurrency and never gets cancelled when the host releases the SDK. `runBlocking` on a caller's main thread can ANR the host. A fire-and-forget `launch` on a long-lived shared scope without `SupervisorJob()` brings the whole scope down on one child's uncaught exception, which is invisible to the host.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. **First pass — runBlocking-anywhere.** Across **every** changed `*.kt` / `*.kts`
   file (production *and* test sources, framework boundaries included), scan for
   any **new** `runBlocking` call site. Each one is flagged for explicit human
   approval per the "Always flag for human approval" section below. This pass
   intentionally ignores the production filter because the approval policy applies
   even to test rules and synchronous-callback bridges.
3. **Second pass — production-scope checks.** Filter to Kotlin production source
   files: keep `*.kt` and `*.kts`, skip paths containing `/src/test/`,
   `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or
   `/src/sharedTest/`. Test-coroutine guidance lives in `android-testing-coroutines`.
4. If no matching files remain after step 3, the production-scope scans emit
   nothing — the first pass may still have findings.
5. On the production files, scan for `GlobalScope`, `runBlocking` reachable from a caller-facing API surface, `CoroutineScope(...)` on long-lived shared scopes missing `SupervisorJob()`, and fire-and-forget `launch` on those shared scopes.

## What to flag

- `GlobalScope.launch { ... }` — no parent, never cancelled, leaks.
  ```kotlin
  // bad
  GlobalScope.launch { uploadTelemetry() }
  // good — inject a scope from the SDK's module-level wiring
  class Telemetry @Inject constructor(
      private val scope: CoroutineScope,
  ) {
      fun upload() { scope.launch { uploadTelemetry() } }
  }
  ```
- `runBlocking` in production code reachable from a caller-facing entry point. The host app's thread is not yours to block.
  ```kotlin
  // bad — public API
  fun getToken(): String = runBlocking { repo.fetchToken() }
  // good
  suspend fun getToken(): String = repo.fetchToken()
  ```
- Long-lived **shared/root** `CoroutineScope` without `SupervisorJob()` — one child's
  failure cancels every sibling and tears the whole scope down. This applies to module-singleton scopes, `@Singleton`/`@AppScope` injected scopes, and class-level scopes that outlive a single operation. Short-lived scopes that intentionally inherit parent failure semantics (or that genuinely want fail-fast across siblings) should keep a normal `Job`.
  ```kotlin
  // bad — long-lived singleton scope that should survive child failures
  @Singleton class TelemetryScope @Inject constructor() {
      val scope = CoroutineScope(Dispatchers.Default)
  }
  // good
  @Singleton class TelemetryScope @Inject constructor() {
      val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
  }
  // also fine — short-lived operation scope where fail-fast is desired
  private val operation = CoroutineScope(Dispatchers.IO)
  ```
- Fire-and-forget `scope.launch { ... }` on a shared scope with no error handling. One uncaught throw silently disappears (or tears the scope down if unsupervised). Add a `CoroutineExceptionHandler` or wrap in `runCatching`.

## What NOT to flag

- Short-lived custom scopes (per-request, per-operation, per-test) without
  `SupervisorJob()`. Fail-fast across siblings is sometimes the desired semantic; only
  long-lived/shared scopes need supervision.
- `viewModelScope` inside `*/apps/demo/**` — those are sample apps and may legitimately use AndroidX ViewModel. The core SDK modules should not depend on `androidx.lifecycle.viewmodel-ktx`; if you see `viewModelScope` in non-demo SDK code, that's worth flagging because the SDK shouldn't bind callers to a specific ViewModel implementation.

## Always flag for human approval

- **Any new `runBlocking`**, even at framework boundaries (OkHttp `Authenticator`,
  test rules, etc.). Existing instances are tolerated, but a new one must be
  explicitly justified by a reviewer — never wave it through.
- **Reusable test setup that calls `runBlocking`** is encouraged when shared across
  tests as a `Rule`/extension — but the `runBlocking` itself still needs explicit
  approval, and sharing the rule is preferred over re-introducing the call site.

## Output

```
<file>:<line> — GlobalScope leaks structured concurrency; inject a scope.
    bad:  GlobalScope.launch { uploadTelemetry() }
    fix:  class Telemetry(private val scope: CoroutineScope) { fun upload() { scope.launch { ... } } }
```
