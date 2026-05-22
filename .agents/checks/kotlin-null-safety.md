---
name: kotlin-null-safety
description: Flag !! abuse and redundant null checks; prefer safe calls, requireNotNull, mapNotNull.
severity-default: high
tools: [Bash, Read, Grep]
---

## Purpose

Non-null assertions (`!!`) crash at runtime when the asserted value is null. Safe-call chains,
`requireNotNull`, and `mapNotNull` keep nullability explicit without crashing. Detekt's
`style` ruleset is OFF in this repo (`static-analysis/config/detekt-rules.yml`), so this
rule is the primary gatekeeper for `!!` discipline in production code.

## Instructions

1. Determine the merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` and `.kts` files outside `/src/test/`, `/src/testDebug/`,
   `/src/testRelease/`, `/src/androidTest/`, and `/src/sharedTest/`. Test code may use
   `!!` liberally to simplify assertions; production code may not.
3. If no matching files remain, emit nothing and exit.
4. Scan each remaining file's diff for `!!`, `filter { it != null }.map { it!! }`, and
   hard `as` casts where the source type is nullable.

## What to flag

- **Any `!!` in non-test code** — no exceptions. Production `!!` is forbidden in this
  repo. Even when the author "knows" the value is non-null, write the discipline
  explicitly with `requireNotNull`, a safe call, or an early return.
  ```kotlin
  // bad
  val name = user.profile!!.name
  // good
  val name = user.profile?.name ?: return
  // good (when absence is a bug)
  val profile = requireNotNull(user.profile) { "profile must be set at this point" }
  val name = profile.name
  ```
- Imperative null-filter plus force-unwrap.
  ```kotlin
  // bad
  val names = users.filter { it.profile != null }.map { it.profile!!.name }
  // good
  val names = users.mapNotNull { it.profile?.name }
  ```
- Hard `as` cast from a nullable or platform type when `as?` would express intent.
  ```kotlin
  // bad
  val album = item as Album
  // good
  val album = item as? Album ?: return
  ```

- Redundant `!!` immediately following `requireNotNull(...)` / `checkNotNull(...)` on
  the same receiver. The guard already narrows to non-null; the `!!` adds nothing and
  reads as if the author didn't trust their own guard. Drop the `!!`.

- Any new `lateinit var` should be evaluated on whether `lateinit` is actually
  necessary. Many occurrences exist for historical reasons; that is not a license to
  add more without justification. Prefer:
  - constructor injection (`val x: X` set in the primary constructor),
  - `by lazy { ... }` when the value is computed on first use,
  - a nullable `var x: X? = null` with explicit handling at the read site.
  Flag a new `lateinit` and ask why one of those alternatives doesn't fit.

## What NOT to flag

- `!!` inside `/src/test/**`, `/src/androidTest/**`, `/src/testDebug/**`,
  `/src/testRelease/**`, or `/src/sharedTest/**` sources.
- Existing `lateinit var` usage that isn't being touched in this diff.

## Output

For each finding emit a block of the form:

```
<file>:<line> — Non-null assertion on uncertain receiver.
    bad:  val name = user.profile!!.name
    fix:  val name = user.profile?.name ?: return   // or requireNotNull(...) if absence is a bug
```

Keep findings focused; prefer at most one finding per logical expression.
