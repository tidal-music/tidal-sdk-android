---
name: android-testing-coroutines
description: Use runTest (not runBlocking) in coroutine tests; require a Main dispatcher rule for tests against code that touches Dispatchers.Main; share TestDispatcher scheduler across fakes.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

`runTest` runs a test body in a `TestScope` backed by a `TestDispatcher`, so virtual time
advances without real sleeps. `runBlocking` in tests blocks the real thread and defeats
`delay` / scheduler control. Code that hops to `Dispatchers.Main` needs a Main dispatcher rule because the JVM does not provide a real main looper.

The SDK uses JUnit 5 with the `kotlinx-coroutines-test` extensions. Prefer those over JUnit 4 idioms where you have a choice.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. **Filter to test sources.** Skip the file if its path does not contain `/src/test/`,
   `/src/testDebug/`, `/src/testRelease/`, `/src/androidTest/`, or `/src/sharedTest/`.
3. If no matching files remain, emit nothing and exit.
4. Scan for `runBlocking { ... }` in test bodies and `TestScope` /
   `StandardTestDispatcher` construction without wiring into fake collaborators across
   **all** matched paths. The Main-dispatcher requirement is **JVM-only** — apply it
   only to files under `/src/test/`, `/src/testDebug/`, `/src/testRelease/`, or
   `/src/sharedTest/`. Skip the rule on `/src/androidTest/` files: instrumentation tests
   run on a real Android main looper, so `Dispatchers.Main` is already available.

## What to flag

- `runBlocking` in a test where `runTest` would work.
  ```kotlin
  // bad
  @Test fun loads() = runBlocking {
      controller.load()
      assertThat(controller.state.value).isEqualTo(Loaded)
  }
  // good
  @Test fun loads() = runTest {
      controller.load()
      assertThat(controller.state.value).isEqualTo(Loaded)
  }
  ```
- Tests against code that uses `Dispatchers.Main` without a Main dispatcher swap.
  Use `Dispatchers.setMain(testDispatcher)` / `Dispatchers.resetMain()` in `@BeforeEach` / `@AfterEach`, or a reusable JUnit 5 extension that wraps the same calls.
  ```kotlin
  // good — JUnit 5 extension wraps setMain / resetMain
  @ExtendWith(MainDispatcherExtension::class)
  class PlaybackTest {
      @Test fun loads() = runTest { ... }
  }
  // also acceptable — inline setup
  class PlaybackTest {
      private val testDispatcher = StandardTestDispatcher()

      @BeforeEach fun setUp() { Dispatchers.setMain(testDispatcher) }
      @AfterEach fun tearDown() { Dispatchers.resetMain() }

      @Test fun loads() = runTest(testDispatcher) { ... }
  }
  ```
- Unshared dispatchers across fakes — virtual time won't advance coherently if each fake
  builds its own `TestScope`.
  ```kotlin
  // bad
  val repo = FakeRepo(TestScope())
  val analytics = FakeAnalytics(TestScope())
  // good
  val scheduler = TestCoroutineScheduler()
  val dispatcher = StandardTestDispatcher(scheduler)
  val repo = FakeRepo(dispatcher)
  val analytics = FakeAnalytics(dispatcher)
  ```

## What NOT to flag

- `runBlocking` in test rules and one-time setup code (`@BeforeAll`, JUnit 5 extension
  callbacks) — not inside a test body.
- Tests that don't touch `Dispatchers.Main` (pure repository / use-case tests) — they
  don't need a Main dispatcher swap.
- Instrumentation tests under `/src/androidTest/` — they execute against a real Android
  main looper, so `Dispatchers.Main` is available without setup.

## Output

```
<file>:<line> — runBlocking in test body; use runTest.
    bad:  @Test fun loads() = runBlocking { controller.load() ... }
    fix:  @Test fun loads() = runTest { controller.load() ... }
```
