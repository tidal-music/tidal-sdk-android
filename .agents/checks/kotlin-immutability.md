---
name: kotlin-immutability
description: Prefer val; never expose MutableList/MutableMap/MutableSet; use _backing property pattern.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

`val` captures immutability of the binding; reassignable state should be rare and intentional.
Exposing `MutableList`/`MutableMap`/`MutableSet` from a class leaks write access to callers
and invalidates any invariant the class would otherwise maintain. The `_backing` pattern
(private mutable + public read-only) is the repo norm for exposing state safely.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for `var ` declarations in class scope, public `MutableList/Map/Set`
   exposures, and `var` accumulators paired with `mutableListOf()`/`mutableMapOf()`.

## What to flag

- `var` with no reassignment visible in the diff.
  ```kotlin
  // bad
  var count = 0
  xs.forEach { count++ }
  return count
  // good
  return xs.size
  ```
- Exposed `MutableList`/`MutableMap`/`MutableSet` as a public property.
  ```kotlin
  // bad
  class Cache {
      val entries: MutableList<Entry> = mutableListOf()
  }
  // good
  class Cache {
      private val _entries = mutableListOf<Entry>()
      val entries: List<Entry> get() = _entries
  }
  ```
- `var` accumulator that a functional pipeline replaces.
  ```kotlin
  // bad
  var total = 0
  for (item in items) { total += item.price }
  // good
  val total = items.sumOf { it.price }
  ```

## What NOT to flag

- `lateinit var` — repo convention (266+ occurrences); treat as idiomatic.
- `private var` that is mutated inside the same class for legitimate state machines.
- `var` in generated code or `@Parcelize` data classes.
- `MutableStateFlow` / `MutableSharedFlow` exposure — covered by `android-stateflow`.

## Output

```
<file>:<line> — Exposed MutableList leaks write access.
    bad:  val entries: MutableList<Entry> = mutableListOf()
    fix:  private val _entries = mutableListOf<Entry>(); val entries: List<Entry> get() = _entries
```
