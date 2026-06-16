#!/usr/bin/env python3
"""Diff two OpenAPI specs and produce a categorized Markdown change report."""

import argparse
import json
import sys


def schema_type_str(schema):
    """Convert an OpenAPI schema to a compact type string."""
    if not schema:
        return "any"
    if "$ref" in schema:
        return schema["$ref"].rsplit("/", 1)[-1]
    if "oneOf" in schema or "anyOf" in schema:
        variants = schema.get("oneOf") or schema.get("anyOf")
        return " | ".join(schema_type_str(v) for v in variants)
    t = schema.get("type", "any")
    if t == "array":
        return f"array[{schema_type_str(schema.get('items', {}))}]"
    if "enum" in schema:
        return f"enum[{', '.join(str(v) for v in schema['enum'])}]"
    fmt = schema.get("format")
    return f"{t}({fmt})" if fmt else t


def get_endpoints(spec):
    """Extract dict of (path, method) -> operation from spec."""
    endpoints = {}
    for path, path_obj in spec.get("paths", {}).items():
        for method in ("get", "post", "put", "patch", "delete"):
            if method in path_obj:
                endpoints[(path, method)] = path_obj[method]
    return endpoints


def resolve_ref(spec, node):
    """Resolve a local "$ref" against the spec, returning the referenced object.

    Returns the node unchanged if it isn't a ref or can't be resolved.
    """
    if not isinstance(node, dict) or "$ref" not in node:
        return node
    ref = node["$ref"]
    if not ref.startswith("#/"):
        return node
    target = spec
    for part in ref[2:].split("/"):
        if not isinstance(target, dict) or part not in target:
            return node
        target = target[part]
    return target


def get_params(operation, spec):
    """Return dict of (name, location) -> resolved parameter for an operation."""
    params = {}
    for param in operation.get("parameters", []):
        resolved = resolve_ref(spec, param)
        name = resolved.get("name")
        location = resolved.get("in")
        if name is not None and location is not None:
            params[(name, location)] = resolved
    return params


def get_schemas(spec):
    """Extract dict of schema_name -> schema from spec."""
    return spec.get("components", {}).get("schemas", {})


def diff_endpoints(old_eps, new_eps):
    """Return added, removed, modified endpoint keys."""
    old_keys = set(old_eps.keys())
    new_keys = set(new_eps.keys())
    added = sorted(new_keys - old_keys)
    removed = sorted(old_keys - new_keys)
    return added, removed


def diff_operations(old_eps, new_eps, old_spec, new_spec):
    """Return per-endpoint parameter changes for endpoints present in both specs.

    Keyed by (path, method), each value is a list of change tuples describing
    parameter additions, removals, required-flips and type changes.
    """
    modified = {}
    for key in sorted(old_eps.keys() & new_eps.keys()):
        old_params = get_params(old_eps[key], old_spec)
        new_params = get_params(new_eps[key], new_spec)

        changes = []
        old_pkeys = set(old_params.keys())
        new_pkeys = set(new_params.keys())

        for name, loc in sorted(new_pkeys - old_pkeys):
            param = new_params[(name, loc)]
            req = "required" if param.get("required", False) else "optional"
            changes.append(
                ("param_added", name, loc, schema_type_str(param.get("schema")), req)
            )

        for name, loc in sorted(old_pkeys - new_pkeys):
            changes.append(("param_removed", name, loc))

        for name, loc in sorted(old_pkeys & new_pkeys):
            old_param = old_params[(name, loc)]
            new_param = new_params[(name, loc)]

            old_type = schema_type_str(old_param.get("schema"))
            new_type = schema_type_str(new_param.get("schema"))
            if old_type != new_type:
                changes.append(("param_type_changed", name, loc, old_type, new_type))

            was_required = old_param.get("required", False)
            now_required = new_param.get("required", False)
            if was_required and not now_required:
                changes.append(("param_became_optional", name, loc))
            elif not was_required and now_required:
                changes.append(("param_became_required", name, loc))

        if changes:
            modified[key] = changes

    return modified


def diff_schemas(old_schemas, new_schemas):
    """Return added, removed, and per-model field changes."""
    old_names = set(old_schemas.keys())
    new_names = set(new_schemas.keys())

    added = sorted(new_names - old_names)
    removed = sorted(old_names - new_names)

    modified = {}
    for name in sorted(old_names & new_names):
        changes = diff_single_schema(old_schemas[name], new_schemas[name])
        if changes:
            modified[name] = changes

    return added, removed, modified


def diff_single_schema(old_schema, new_schema):
    """Compare two schemas and return a list of change descriptions."""
    changes = []
    old_props = old_schema.get("properties", {})
    new_props = new_schema.get("properties", {})
    old_required = set(old_schema.get("required", []))
    new_required = set(new_schema.get("required", []))

    old_field_names = set(old_props.keys())
    new_field_names = set(new_props.keys())

    for f in sorted(new_field_names - old_field_names):
        req = "required" if f in new_required else "optional"
        changes.append(("added_field", f, schema_type_str(new_props[f]), req))

    for f in sorted(old_field_names - new_field_names):
        changes.append(("removed_field", f))

    for f in sorted(old_field_names & new_field_names):
        old_type = schema_type_str(old_props[f])
        new_type = schema_type_str(new_props[f])
        if old_type != new_type:
            changes.append(("type_changed", f, old_type, new_type))

        was_required = f in old_required
        now_required = f in new_required
        if was_required and not now_required:
            changes.append(("became_optional", f))
        elif not was_required and now_required:
            changes.append(("became_required", f))

    return changes


def format_param_change(path, method, change):
    """Format a single parameter change tuple. Returns (severity, message)."""
    ep = f"{method.upper()} {path}"
    kind = change[0]
    if kind == "param_removed":
        name, loc = change[1], change[2]
        return "breaking", f"`{ep}`: removed {loc} parameter `{name}`"
    if kind == "param_type_changed":
        name, loc, old_type, new_type = change[1], change[2], change[3], change[4]
        return (
            "breaking",
            f"`{ep}`: {loc} parameter `{name}` type changed `{old_type}` -> `{new_type}`",
        )
    if kind == "param_became_required":
        name, loc = change[1], change[2]
        return "breaking", f"`{ep}`: {loc} parameter `{name}` became required"
    if kind == "param_added":
        name, loc, ptype, req = change[1], change[2], change[3], change[4]
        severity = "breaking" if req == "required" else "additive"
        return severity, f"`{ep}`: added {req} {loc} parameter `{name}` ({ptype})"
    if kind == "param_became_optional":
        name, loc = change[1], change[2]
        return "additive", f"`{ep}`: {loc} parameter `{name}` became optional"
    return "additive", f"`{ep}`: {kind} `{change[1]}`"


def format_report(
    old_spec, new_spec, ep_added, ep_removed, op_modified, s_added, s_removed, s_modified
):
    """Format the change report as Markdown."""
    old_version = old_spec.get("info", {}).get("version", "unknown")
    new_version = new_spec.get("info", {}).get("version", "unknown")

    breaking = []
    additive = []

    # Classify endpoint changes
    for path, method in ep_removed:
        breaking.append(f"REMOVED endpoint: `{method.upper()} {path}`")
    for path, method in ep_added:
        additive.append(f"New endpoint: `{method.upper()} {path}`")

    # Classify parameter changes on endpoints present in both specs
    for (path, method), changes in sorted(op_modified.items()):
        for change in changes:
            severity, message = format_param_change(path, method, change)
            (breaking if severity == "breaking" else additive).append(message)

    # Classify schema changes
    for name in s_removed:
        breaking.append(f"REMOVED model: `{name}`")
    for name in s_added:
        additive.append(f"New model: `{name}`")

    for name, changes in sorted(s_modified.items()):
        for change in changes:
            if change[0] == "removed_field":
                breaking.append(f"`{name}`: removed field `{change[1]}`")
            elif change[0] == "type_changed":
                breaking.append(f"`{name}`.`{change[1]}`: type changed `{change[2]}` -> `{change[3]}`")
            elif change[0] == "became_required":
                breaking.append(f"`{name}`.`{change[1]}`: became required")
            elif change[0] == "added_field":
                additive.append(f"`{name}`: added field `{change[1]}` ({change[3]}, {change[2]})")
            elif change[0] == "became_optional":
                additive.append(f"`{name}`.`{change[1]}`: became optional")

    lines = []
    lines.append("## API Change Analysis")
    lines.append(f"**Spec version**: {old_version} -> {new_version}")

    if not breaking and not additive:
        lines.append("")
        lines.append("No API surface changes detected.")
        return "\n".join(lines)

    lines.append(
        f"**Breaking changes: {len(breaking)}** | Additive changes: {len(additive)}"
    )
    lines.append("")

    if breaking:
        lines.append("### Breaking Changes")
        lines.append("")
        for item in breaking:
            lines.append(f"- {item}")
        lines.append("")

    if additive:
        lines.append("### Additive Changes")
        lines.append("")
        for item in additive:
            lines.append(f"- {item}")
        lines.append("")

    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(
        description="Analyze changes between two OpenAPI specs"
    )
    parser.add_argument("--old-spec", required=True, help="Path to old OpenAPI JSON spec")
    parser.add_argument("--new-spec", required=True, help="Path to new OpenAPI JSON spec")
    args = parser.parse_args()

    with open(args.old_spec, "r") as f:
        old_spec = json.load(f)
    with open(args.new_spec, "r") as f:
        new_spec = json.load(f)

    old_eps = get_endpoints(old_spec)
    new_eps = get_endpoints(new_spec)
    ep_added, ep_removed = diff_endpoints(old_eps, new_eps)
    op_modified = diff_operations(old_eps, new_eps, old_spec, new_spec)

    old_schemas = get_schemas(old_spec)
    new_schemas = get_schemas(new_spec)
    s_added, s_removed, s_modified = diff_schemas(old_schemas, new_schemas)

    report = format_report(
        old_spec, new_spec, ep_added, ep_removed, op_modified, s_added, s_removed, s_modified
    )
    print(report)

    # Log breaking changes to stderr so they're visible in CI logs
    breaking_items = []
    for path, method in ep_removed:
        breaking_items.append(f"  REMOVED endpoint: {method.upper()} {path}")
    for (path, method), changes in sorted(op_modified.items()):
        for change in changes:
            severity, message = format_param_change(path, method, change)
            if severity == "breaking":
                breaking_items.append("  " + message.replace("`", ""))
    for name in s_removed:
        breaking_items.append(f"  REMOVED model: {name}")
    for name, changes in sorted(s_modified.items()):
        for change in changes:
            if change[0] == "removed_field":
                breaking_items.append(f"  {name}: removed field {change[1]}")
            elif change[0] == "type_changed":
                breaking_items.append(
                    f"  {name}.{change[1]}: type changed {change[2]} -> {change[3]}"
                )
            elif change[0] == "became_required":
                breaking_items.append(f"  {name}.{change[1]}: became required")

    if breaking_items:
        print(
            f"\n⚠️  {len(breaking_items)} breaking change(s) detected:\n"
            + "\n".join(breaking_items),
            file=sys.stderr,
        )


if __name__ == "__main__":
    main()
