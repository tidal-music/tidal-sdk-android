---
name: kotlin-idioms
description: Flag non-idiomatic block bodies for single expressions, redundant types, and wrong scope-function choice.
severity-default: low
tools: [Bash, Read, Grep]
---

## Purpose

Expression bodies, type inference on locals, and the right scope function each make code
shorter and easier to read at a glance. Using the wrong scope function (`apply` where
`also` fits, `let` where `run` fits) adds friction without changing behaviour.

## Instructions

1. Determine merge-base and changed files:
   ```bash
   merge_base="${MERGE_BASE:-$(git merge-base origin/HEAD HEAD)}"
   git diff --name-only "$merge_base"...HEAD
   ```
2. Filter to `.kt` files. If none, emit nothing and exit.
3. Scan added lines for single-statement block-body functions, redundant local types
   (`val x: String = "..."`), and scope-function misuse (`apply { it.foo() }`, `run` when
   the lambda body returns a receiver reference).

## What to flag

- Single-expression function written with block body.
  ```kotlin
  // bad
  fun double(x: Int): Int { return x * 2 }
  // good
  fun double(x: Int): Int = x * 2
  ```
- Redundant type annotation on an obvious local.
  ```kotlin
  // bad
  val name: String = "Alice"
  // good
  val name = "Alice"
  ```
- `let` used where the receiver is unchanged and the result is the receiver.
  ```kotlin
  // bad
  return builder.let { it.build(); it }
  // good
  return builder.apply { build() }
  ```
- `also` used where another scope function is a better fit. `also` should appear only
  when it's specifically the right tool (side-effect with explicit `it`, returns the
  receiver). Prefer `apply`/`run`/`let` where they fit; do not rewrite `apply` into
  `also` for cosmetic reasons.

## What NOT to flag

- Public API signatures with explicit return types — explicit types aid ABI stability,
  keep them.
- Block bodies with intermediate `val` bindings for clarity.
- Expression bodies on multi-line lambdas where the block body aids readability.

## Output

```
<file>:<line> — Single-expression function; use expression body.
    bad:  fun double(x: Int): Int { return x * 2 }
    fix:  fun double(x: Int): Int = x * 2
```

### Scope-function cheat sheet

| Fn      | Receiver | Return           | Use when                                       |
| ------- | -------- | ---------------- | ---------------------------------------------- |
| `let`   | `it`     | lambda result    | Null-check chain, transform nullable to value. |
| `run`   | `this`   | lambda result    | Compute a value from receiver fields.          |
| `apply` | `this`   | receiver         | Configure a builder-like receiver.             |
| `also`  | `it`     | receiver         | Side-effect with explicit receiver reference.  |
| `with`  | `this`   | lambda result    | Non-null receiver, block-scoped member access. |
