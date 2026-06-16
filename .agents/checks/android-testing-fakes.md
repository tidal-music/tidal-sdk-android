---
name: android-testing-fakes
description: Prefer hand-written Fake<Interface> for SDK-owned interfaces; mockito-kotlin mock() only for final/Android-framework types; assert state not interactions.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Fakes (hand-written implementations of SDK-owned interfaces) survive refactors that mocks
do not: rename a method and the fake fails to compile, while the mock stays green with a
stale `whenever(...)`. Mocks remain valuable for Android framework types (`Context`,
`Intent`) and third-party final classes that cannot be subclassed cleanly. Assertions on
mock interactions couple the test to implementation; assertions on state survive
refactors.

The SDK uses `mockito-kotlin` as its mocking library — this rule is **not** an argument against the library; it's an argument for choosing the right tool per test.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. **Filter to test sources.** Skip the file if its path does not contain `/src/test/`,
   `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or `/src/sharedTest/`.
3. If no matching files remain, emit nothing and exit.
4. Scan added lines for `mock<SdkInterface>()` where a `FakeXxx` exists nearby (sibling
   `fakes/` package, the module's test source set, or the `common` module's test
   utilities), and for `verify(mock).someMethod(...)` assertions where a state check on the fake would suffice.

## What to flag

- `mock<T>()` for an SDK-owned interface that has a canonical Fake.
  ```kotlin
  // bad
  val tokens = mock<TokenProvider>()
  whenever(tokens.current()).thenReturn(Token("abc"))
  // good — use the existing fake
  val tokens = FakeTokenProvider().apply { setCurrent(Token("abc")) }
  ```
- `mock<T>()` for an SDK-owned interface where **no fake exists yet**. Recommend
  introducing a canonical fake (`Fake<Interface>`) instead — even one consumer is
  enough reason to start one, since the next consumer will reuse it. Put the fake in the
  same module's test source set (or in a sibling `fakes/` package the test imports).
- A second test introducing a duplicate ad-hoc fake/mock for an interface that already
  has (or just got) a canonical `FakeXxx`. Promote the existing fake to a shared
  location (e.g. the module's `src/test/kotlin/.../fakes/` or a shared test utility
  module) and reuse it.
- A relaxed mock (`mock<T>(defaultAnswer = RETURNS_DEEP_STUBS)` or similar) on an
  SDK-owned interface. A fake that records invocations is easier to assert on than a
  permissive mock that silently returns defaults.
- Test assertion that checks mock interactions instead of observable state.
  ```kotlin
  // bad
  controller.start()
  verify(repo).fetch(anyOrNull())
  // good
  controller.start()
  assertThat(controller.state.value).isInstanceOf(State.Loaded::class)
  ```

## What NOT to flag

- `mock<Context>()`, `mock<Intent>()`, `mock<NotificationManager>()` — Android framework
  classes; mocking is fine.
- `mock<T>()` for a final third-party class (Retrofit `Response`, OkHttp interceptor types, third-party SDK types) — cannot realistically subclass.
- `lateinit var` on test properties — common JUnit idiom, allowed.
- `verify(...)` on side-effect-only collaborators (event dispatchers, logger) where there is no state to assert on.

## Output

```
<file>:<line> — Mocking an SDK interface with an existing Fake.
    bad:  val tokens = mock<TokenProvider>(); whenever(tokens.current()).thenReturn(Token("abc"))
    fix:  val tokens = FakeTokenProvider().apply { setCurrent(Token("abc")) }
```
