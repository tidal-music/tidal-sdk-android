---
name: kotlin-sealed-types
description: Prefer sealed class/interface for restricted hierarchies; data class/data object for value/singleton variants; avoid else branches on exhaustive when.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Sealed hierarchies give the compiler enough information to check `when` exhaustiveness and
let IDEs surface every case at refactor time. A spurious `else` branch defeats that — a
new variant added later compiles but reaches the wrong branch at runtime. `data class` and
`data object` provide the standard equals/hashCode/copy/toString contract for value types.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for `abstract class` hierarchies with a closed set of known subclasses,
   `when (x)` blocks on a sealed receiver with an `else -> throw`/`else -> error` tail,
   and public `Pair<X, Y>`/`Triple<X, Y, Z>` API signatures.

## What to flag

- `abstract class` with a closed set of subclasses — prefer `sealed class`.
  ```kotlin
  // bad
  abstract class ApiResult
  class Success(val data: Data) : ApiResult()
  class Failure(val error: Throwable) : ApiResult()
  // good
  sealed class ApiResult {
      data class Success(val data: Data) : ApiResult()
      data class Failure(val error: Throwable) : ApiResult()
  }
  ```
- `when` on a sealed receiver with a redundant `else` branch.
  ```kotlin
  // bad
  when (state) {
      is Loading -> showSpinner()
      is Loaded -> showData(state.data)
      else -> error("unreachable")
  }
  // good
  when (state) {
      is Loading -> showSpinner()
      is Loaded -> showData(state.data)
  }
  ```
- Public API returning `Pair<X, Y>` for a domain concept.
  ```kotlin
  // bad
  fun locate(id: Id): Pair<Track, Album>
  // good
  data class TrackLocation(val track: Track, val album: Album)
  fun locate(id: Id): TrackLocation
  ```

## What NOT to flag

- `Pair` / `Triple` as local temporaries inside a function.
- `else` on a non-sealed receiver (`String`, `Int`) — that branch is load-bearing.
- `abstract class` where subclasses are user-extended (public SPI).

## Output

```
<file>:<line> — abstract class with known subclasses; use sealed class for when exhaustiveness.
    bad:  abstract class ApiResult
    fix:  sealed class ApiResult
```
