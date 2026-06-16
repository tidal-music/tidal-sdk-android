---
name: kotlin-imports
description: Flag fully qualified class names used inline in code; require imports instead.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

Idiomatic Kotlin uses `import` statements at the top of the file and refers to types,
functions, and properties by their short name in the body. Inline fully qualified names
(FQNs) like `com.tidal.foo.bar.Baz()` mid-file are a Java/IDE-paste smell — they make
declarations harder to read, hide dependencies from the import block, and frequently
arrive when an LLM or auto-complete inserted a type without resolving its import.

There is **no legitimate reason** to inline an FQN. Kotlin supports import aliases
(`import x.y.Z as Alias`), so even when two classes share a simple name across
different packages, both can be imported — the rarer one with an alias — and referenced
by short name in the body. Always flag inline FQNs.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` / `.kts` files. If none, emit nothing and exit.
3. For each changed file, identify **inline references to a fully qualified name** on
   added lines. An inline FQN is a dotted reference whose leading segment is a known
   package root (e.g. `com.`, `org.`, `io.`, `net.`, `java.`, `javax.`, `kotlin.`,
   `kotlinx.`, `androidx.`, `android.`, `dagger.`, `retrofit2.`, `okhttp3.`, `coil.`)
   used outside an `import` statement, the `package` declaration, KDoc references
   (`[com.foo.Bar]`), and string literals. The final segment may be a class
   (`com.foo.Bar`), a top-level function (`kotlinx.coroutines.flow.flowOf`), a
   property, an annotation, or a single-package type (`retrofit2.Response`,
   `okhttp3.Interceptor`).

   Use your judgment — `user.profile.displayName` is a property chain on a local, not
   an FQN. `com.tidal.foo.Bar` starts with a known package root, so it is. When
   uncertain, check whether the leading identifier resolves as a value in scope (local,
   parameter, import) versus as a package root.
4. Before flagging, confirm the simple name is **not already imported** at the top of
   the same file (if it is imported, the inline FQN is redundant — still flag it).
5. Suppress the cases listed in **What NOT to flag**.

## What to flag

- Inline FQN for a type, constructor, function, or property reference where an import
  would work.
  ```kotlin
  // bad
  class UserRepository(
      private val api: com.tidal.network.UserApi,
  ) {
      fun stream(): kotlinx.coroutines.flow.Flow<User> =
          kotlinx.coroutines.flow.flowOf(...)
  }

  // good
  import com.tidal.network.UserApi
  import kotlinx.coroutines.flow.Flow
  import kotlinx.coroutines.flow.flowOf

  class UserRepository(
      private val api: UserApi,
  ) {
      fun stream(): Flow<User> = flowOf(...)
  }
  ```
- Redundant FQN when the simple name is already imported in the same file (dead giveaway
  the FQN was pasted in without checking the import block).
- FQN in a function signature, property type, generic bound, `is`/`as` cast, annotation,
  or default-argument expression — all of these accept normal imports.
- Same-name collision "solved" with an inline FQN. Use an import alias instead.
  ```kotlin
  // bad
  import java.util.Date

  fun parse(sqlDate: java.sql.Date): Date = Date(sqlDate.time)

  // good
  import java.util.Date
  import java.sql.Date as SqlDate

  fun parse(sqlDate: SqlDate): Date = Date(sqlDate.time)
  ```

## What NOT to flag

- **Reflection / class literals** that intentionally name a type that is not a
  dependency (e.g. logging strings, `Class.forName("...")`, error messages quoting a
  class name).
- **`@JvmName`, `@file:JvmName`, package declarations,** and other annotation values
  that require a string FQN.
- **KDoc references** like `[com.tidal.foo.Bar]` — these are documentation links, not
  code.
- **String literals** containing dotted paths (log tags, intent actions, deep-link
  patterns).
- **Generated code** (`build/`, files with `@Generated`, Hilt/Anvil/Dagger output).
- **Pre-existing FQNs** not introduced by this diff. Only flag added lines.

## Output

```
<file>:<line> — Inline fully qualified name; add an import and use the short name.
    bad:  private val api: com.tidal.network.UserApi
    fix:  import com.tidal.network.UserApi
          ...
          private val api: UserApi
```
