---
name: kotlin-collections
description: Flag imperative for-loops and inefficient combinators; prefer map/filter/first{}/any{}/partition/mapNotNull.
severity-default: medium
tools: [Bash, Read, Grep]
---

## Purpose

Kotlin's collection operators express intent more directly than imperative loops and let
the compiler specialize common patterns. Some combinator chains also allocate unnecessary
intermediate lists; the right single operator avoids the allocation.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for `for (` loops on collections, `.filter { ... }.map { ... }` chains
   with a null-guard, `.filter { ... }.first()` / `.filter { ... }.size`, and
   `.groupBy { }.mapValues { it.value.size }`.

## What to flag

- Imperative loop that can be a functional lookup.
  ```kotlin
  // bad
  for (x in xs) {
      if (x.valid) return x
  }
  return null
  // good
  return xs.firstOrNull { it.valid }
  ```
- `filter + map` with `!!` or null-guard.
  ```kotlin
  // bad
  users.filter { it.profile != null }.map { it.profile!!.name }
  // good
  users.mapNotNull { it.profile?.name }
  ```
- `filter + size` used as a boolean.
  ```kotlin
  // bad
  if (items.filter { it.isActive }.size > 0) { ... }
  // good
  if (items.any { it.isActive }) { ... }
  ```
- `filter + size` used as a count.
  ```kotlin
  // bad
  val n = items.filter { it.isActive }.size
  // good
  val n = items.count { it.isActive }
  ```
- `groupBy + mapValues { it.value.size }`.
  ```kotlin
  // bad
  items.groupBy { it.category }.mapValues { it.value.size }
  // good
  items.groupingBy { it.category }.eachCount()
  ```
- Partition written manually with two filters.
  ```kotlin
  // bad
  val enabled = items.filter { it.on }
  val disabled = items.filter { !it.on }
  // good
  val (enabled, disabled) = items.partition { it.on }
  ```

## What NOT to flag

- Loops with side effects that cannot be expressed as `forEach` cleanly (multiple output
  collections, early exit via `break`/`continue` with complex conditions, index-sensitive
  mutation).
- `asSequence()` absence on chains over small collections (< ~1000 elements) — the
  allocation overhead of a sequence wrapper outweighs the savings for small inputs.
- Loops inside RecyclerView `onBindViewHolder` or other hot-path code where the team has
  intentionally chosen imperative style for allocation reasons (leave a TODO instead of
  a finding).

## Output

```
<file>:<line> — Imperative loop where first{} suffices.
    bad:  for (x in xs) { if (x.valid) return x }; return null
    fix:  return xs.firstOrNull { it.valid }
```
