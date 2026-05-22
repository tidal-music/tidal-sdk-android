---
name: kotlin-naming
description: Flag abbreviated identifiers, wrong acronym casing, missing internal visibility on impl classes, and wrong _backing field naming.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

Names are read far more often than they are written. The repo prefers the **full word**
over abbreviations everywhere — file names, class names, function names, parameter
names, local variables. Kotlin style capitalizes acronyms like any other word once
they exceed two letters (`XmlParser`, not `XMLParser`). `internal` visibility keeps
implementation classes out of module public API when nothing outside the module needs
them. Backing properties follow the `_name` + `name` convention so that the private
mutable form is obviously paired with the public read-only exposure.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for **CamelCase** identifiers (types, functions, regular
   properties) containing runs of 3+ uppercase letters, new public classes in
   `*Impl.kt` files without `internal`, and private backing fields not prefixed with
   `_`. Skip `SCREAMING_SNAKE_CASE` constants (`const val MAX_RETRY_COUNT`) — that is
   the standard Kotlin style for compile-time constants and is not an acronym-casing
   smell.

## What to flag

- Abbreviated identifier where a full word reads more clearly. Applies to **any**
  identifier introduced by the diff — file names, class names, function names,
  parameter names, local variables. Common offenders: `Ext`, `Mgr`, `Util`, `Config`
  (when meaning *configuration*), `Conn`, `Auth` (as a noun, not the established
  module name), `Btn`, `Ctx`, `Fmt`, `Idx`, `Tmp`. Use `Extensions`, `Manager`,
  `Connection`, `Configuration`, etc.
  ```kotlin
  // bad
  // file: StringExt.kt
  class DownloadMgr
  fun fmtDuration(d: Long): String
  val tmpFile: File
  // good
  // file: StringExtensions.kt
  class DownloadManager
  fun formatDuration(durationMillis: Long): String
  val temporaryFile: File
  ```
  Established short forms that are project-wide vocabulary (`Db` for database in
  existing names, `Url`, `Id`, `Vm` for ViewModel where the convention is in use)
  do not need to be flagged.
- Acronym of 3+ letters in ALL CAPS.
  ```kotlin
  // bad
  class HTTPClient
  fun parseXMLDocument()
  // good
  class HttpClient
  fun parseXmlDocument()
  ```
- Implementation class declared public when it doesn't need to be reached from outside
  the module. The repo norm is the inverse of the Kotlin default: only mark a class
  `internal` when it should **not** be accessible — but conversely, only leave an
  `*Impl` class public when an out-of-module caller actually needs it (DI graph wiring,
  cross-module factory). When in doubt, ask: "does anything outside this module
  reference this type by name?" If no, recommend `internal`. If yes, public is fine.
  ```kotlin
  // flag — no out-of-module caller; should be internal
  class DefaultAuthRepository @Inject constructor(...) : AuthRepository { ... }
  // also fine — public when a sibling module's DI module needs to bind it directly
  class DefaultAuthRepository @Inject constructor(...) : AuthRepository { ... }
  ```
- Backing property missing `_` prefix.
  ```kotlin
  // bad
  private val stateFlow = MutableStateFlow<X>(...)
  val state: StateFlow<X> = stateFlow
  // good
  private val _state = MutableStateFlow<X>(...)
  val state: StateFlow<X> = _state
  ```

## What NOT to flag

- Two-letter acronyms (`UIState`, `IOError`) — Kotlin style allows 2-letter runs.
- `class UI`, `class OS` — standalone short acronyms.
- `SCREAMING_SNAKE_CASE` constants (`const val MAX_RETRY_COUNT`) — standard Kotlin
  style, not an acronym smell.
- Public Dagger `@Component` / Anvil-generated classes that must be public by contract.
- Pre-existing names that are not part of this diff (focus on the added lines).

## Output

```
<file>:<line> — Acronym casing; convert 3+ letter run to title case.
    bad:  class HTTPClient
    fix:  class HttpClient
```
