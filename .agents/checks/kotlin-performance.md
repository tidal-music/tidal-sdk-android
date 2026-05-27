---
name: kotlin-performance
description: Prefer const val (in the right scope) and use inline where it's actually needed.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

`const val` inlines a primitive/String constant at call sites and signals to readers
that a value is a true compile-time constant. `inline` on a higher-order function
removes the `Function` allocation for the lambda parameter when the function actually
needs that.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for top-level `val NAME = "literal"` / `val NAME = 42`, file-local
   `const val`s that belong inside a class's companion object, and higher-order
   functions where `inline` would be appropriate.

## What to flag

- Top-level or companion `val` with a compile-time constant initialiser — use `const val`.
  ```kotlin
  // bad
  val BASE_URL = "https://api.example.com"
  // good
  const val BASE_URL = "https://api.example.com"
  ```
- File-level `const val` that is clearly associated with — or only used by — a single
  class. Move it into that class's `companion object` so the constant is co-located
  with the type that owns its meaning.
  ```kotlin
  // bad — top-level const used only inside ChartViewModel
  private const val MAX_POINTS = 1000
  class ChartViewModel { ... uses MAX_POINTS ... }
  // good
  class ChartViewModel {
      ... uses MAX_POINTS ...
      companion object { private const val MAX_POINTS = 1000 }
  }
  ```
- Higher-order function declared without `inline` where `inline` is actually needed
  (lambda allocation matters in a hot path, or the function takes a `crossinline` /
  `noinline` / reified type parameter that requires it). The judgment call is
  "is `inline` needed here?" — apply it where it is, leave it off where it isn't.

## What NOT to flag

- `val` whose initialiser is not a compile-time constant (`val x = computeSomething()`).
- `inline` declined on a function where `inline` is not needed (most functions). Only
  flag missing `inline` when the call site genuinely benefits.

## Output

```
<file>:<line> — Compile-time constant; mark const val.
    bad:  val BASE_URL = "https://api.example.com"
    fix:  const val BASE_URL = "https://api.example.com"
```
