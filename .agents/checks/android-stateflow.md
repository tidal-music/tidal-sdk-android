---
name: android-stateflow
description: Flag exposed MutableStateFlow in public API; recommend update{} for read-modify-write.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Exposed `MutableStateFlow` lets callers mutate state the owner is meant to control. In a library that's worse than in an app — host apps can corrupt SDK invariants through the public surface. `_state.value = _state.value.copy(...)` races under concurrent updates; `update { }` serialises the CAS.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. **Filter by import.** Skip the file if it does not import any of
   `kotlinx.coroutines.flow.StateFlow`, `kotlinx.coroutines.flow.MutableStateFlow`,
   `kotlinx.coroutines.flow.SharedFlow`, or `kotlinx.coroutines.flow.MutableSharedFlow`.
3. If no matching files remain, emit nothing and exit.
4. Scan added lines for public `MutableStateFlow` / `MutableSharedFlow` properties and
   read-modify-write on `.value`.

## What to flag

- Public `MutableStateFlow` exposing write access.
  ```kotlin
  // bad
  class PlayerState {
      val playback = MutableStateFlow(Playback.Idle)
  }
  // good — _backing pattern
  class PlayerState {
      private val _playback = MutableStateFlow<Playback>(Playback.Idle)
      val playback: StateFlow<Playback> = _playback
  }
  ```
- `.value = .value.copy(...)` read-modify-write without `update { }`.
  ```kotlin
  // bad
  _state.value = _state.value.copy(loading = true)
  // good
  _state.update { it.copy(loading = true) }
  ```

- `SharingStarted.Eagerly` on `stateIn` — needs explicit justification. Eagerly keeps
  the upstream collection alive for the lifetime of the scope regardless of subscribers,
  which is rarely what library flows want (you can pin the host's resources indefinitely).
  Flag every new occurrence and ask the author why `WhileSubscribed(5_000)` (or `Lazily`) is not sufficient. **When in doubt, flag.**
  ```kotlin
  // flag — needs reviewer to justify why Eagerly is required here
  val state = repo.stream().stateIn(scope, SharingStarted.Eagerly, Playback.Idle)
  // typical default
  val state = repo.stream().stateIn(scope, SharingStarted.WhileSubscribed(5_000), Playback.Idle)
  ```

## What NOT to flag

- `override val state: StateFlow<X> = _state` raw assignment — idiomatic when an interface contract declares the read-only type. Do NOT demand `.asStateFlow()`.
- `_state.value = newValue` with a full replacement (not a copy of the prior value) —
  that is a set, not a read-modify-write.

## Output

```
<file>:<line> — Public MutableStateFlow leaks write access.
    bad:  val playback = MutableStateFlow(Playback.Idle)
    fix:  private val _playback = MutableStateFlow<Playback>(Playback.Idle); val playback: StateFlow<Playback> = _playback
```
