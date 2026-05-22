# `.agents/`

This directory hosts files consumed by agentic tooling for this repo:

- **`checks/`** — review rules that `sq agents review` loads as parallel sub-agents. Each `.md` file under `checks/` is one standalone review sub-agent. Its frontmatter configures the runner; its body tells the sub-agent what to look for.
- **`do.md`** — repo-specific instructions for the `/do` skill (toolchain commands, formatter handling, atomic-unit exceptions, language style routing, banned commands, branch naming, module paths). Read by the skill at task start and folded into the task context bundle.

## How `sq agents review` uses `checks/`

The runner discovers checks from three tiers:

1. **Global** — `~/.config/amp/checks/*.md` (synced from `squareup/agents/checks/`).
2. **Repo root** — `.agents/checks/*.md` (this directory).
3. **Subdirectory** — `<dir>/.agents/checks/*.md` (only fires when the diff touches that subtree).

For one review run the runner:

1. Resolves merge-base via `git merge-base <base> HEAD` and reads each rule's `.md` file from the **merge-base revision** (so PR authors can't relax rules in the same PR they're shipping a violation in).
2. Slices the diff (changed files between merge-base and HEAD) and passes that list to every sub-agent.
3. **Spawns one sub-agent per `.md` file in parallel.** Each rule self-filters at the top of its `## Instructions` (path/file-type guards) so unrelated rules exit immediately on a typical diff.
4. Aggregates findings, applies `severity-default` from each rule's frontmatter, and renders one consolidated review.

The same `.md` files drive local dev (`sq agents review` on a feature branch), CI (every PR push), and other agent workflows (`/do`, `/review`) — one source of truth, multiple execution paths.

## Philosophy

- **Flat layout, self-filtering bodies.** Every check lives at `.agents/checks/` at repo root. Rules that apply only to test sources, Android UI, build files, etc. open their `## Instructions` with a path or file-type guard so the sub-agent skips non-matching files cheaply.
- **Coexist, don't replace.** `.editorconfig`, `static-analysis/`, CI workflows, and repo conventions stay as-is. These checks are additive.
- **Don't re-litigate formatting.** ktfmt owns whitespace and layout (`static-analysis/run-ktfmt.sh`, `.editorconfig`). Checks here concern structure and idioms only.

## What's in `checks/`

Rules ported from the TIDAL Android client's `.agents/checks/` and adapted to the SDK's reality (Kotlin 2.x, Dagger + KSP, JUnit 5 + mockito-kotlin, library modules instead of an Android app):

### Kotlin language-level (copied verbatim — generic enough to apply everywhere)

- `kotlin-collections` — imperative loops, inefficient combinators.
- `kotlin-coroutines` — `runBlocking` in suspend, swallowed `CancellationException`, `Thread.sleep` in coroutines.
- `kotlin-error-handling` — empty catches, exception-as-control-flow.
- `kotlin-idioms` — block bodies on single-expression functions, redundant locals.
- `kotlin-immutability` — `var` where `val` fits, exposed mutable collections.
- `kotlin-imports` — wildcard imports.
- `kotlin-java-isms` — Java-style getters, util `object`s.
- `kotlin-naming` — abbreviated identifiers, acronym casing.
- `kotlin-null-safety` — `!!` in non-test code.
- `kotlin-performance` — top-level constants without `const val`, missing `inline`.
- `kotlin-preconditions` — manual `throw IllegalArgumentException` instead of `require/check`.
- `kotlin-sealed-types` — `else` on exhaustive `when`.

### SDK-adapted (Android / coroutine / build patterns rewritten for library code)

- `android-build-tooling` — version catalog enforcement (`gradle/libs.versions.toml`); kapt-vs-KSP tripwire.
- `android-coroutine-scopes` — `GlobalScope`, `runBlocking` on caller threads, fire-and-forget launches, missing `SupervisorJob` on long-lived scopes.
- `android-stateflow` — exposed `MutableStateFlow`, missing `update {}`, new `SharingStarted.Eagerly`.
- `android-testing-coroutines` — `runBlocking` in tests, Main-dispatcher setup, shared `TestDispatcher` scheduler across fakes.
- `android-testing-fakes` — prefer `FakeXxx` over `mock<SdkInterface>()`; assert state, not interactions.

### Intentionally not ported

These TIDAL Android client checks are tightly coupled to the app's feature-module architecture and don't apply to the SDK:

- `architecture` (feature/<x>/<data|domain|ui> shape, `:library` aggregator policy).
- `temp-no-expansion` (the app's `/temp/` legacy holding pen).
- `compose` (Market design system, screen shape, `MarketContext` theming).
- `android-lifecycle` (Compose-only-for-new-UI; the SDK doesn't host UI).

If a future SDK module grows enough UI surface to warrant its own architecture / Compose rules, port these on demand and re-target them at that module's source tree.

## Repo-reality notes

These are SDK-wide overrides the source skills don't know about. Each check file restates the relevant ones in its own `## What NOT to flag`, but they're collected here so a reviewer can see the policy at a glance.

- **Test framework**: JUnit 5 + mockito-kotlin + AssertJ-style assertions. Examples use `mock<T>()` / `whenever(...).thenReturn(...)` / `verify(...)`. Not MockK, not Truth.
- **DI**: Dagger (`dagger.compiler` via KSP). Not Anvil, not Hilt.
- **Annotation processing**: KSP everywhere. Flag any new `kapt` usage per `android-build-tooling`.
- **Coroutine scopes**: long-lived scopes are SDK-owned and injected; do not introduce `viewModelScope` in non-demo modules.
- **`runBlocking`**: any new call site needs explicit human approval, even at framework boundaries. Pre-existing call sites are tolerated. See `android-coroutine-scopes`.
- **`SharingStarted.Eagerly`**: every new occurrence needs explicit reviewer justification — when in doubt, flag. See `android-stateflow`.
- **Demo apps under `*/apps/demo/`**: minimal sample apps. They may legitimately use AndroidX `viewModelScope` and Compose. The core SDK modules should not.

## Severity calibration

- `high` — Error-equivalent. Issues that should block a merge.
  - `kotlin-null-safety` (`!!` in non-test code).
  - `kotlin-coroutines` (`runBlocking` in suspend, swallowed `CancellationException`, `Thread.sleep` in coroutines).
  - `android-coroutine-scopes` (`GlobalScope`, `runBlocking` on caller threads, missing `SupervisorJob()` on long-lived scope).
- `medium` — Warning. Non-idiomatic but works.
  - `kotlin-collections`, `kotlin-error-handling`, `kotlin-immutability`, `kotlin-preconditions`, `kotlin-sealed-types`.
  - `android-stateflow` (exposed `MutableStateFlow`, missing `update {}`, new `SharingStarted.Eagerly`).
  - `android-testing-coroutines` (`runBlocking` in test where `runTest` fits).
  - `android-testing-fakes` (`mock<SdkInterface>` when a fake exists).
- `low` — Info. Style / modernisation.
  - `kotlin-idioms`, `kotlin-imports`, `kotlin-java-isms`, `kotlin-naming`, `kotlin-performance`.
  - `android-build-tooling` (inline `"group:artifact:version"` strings instead of catalog references).
