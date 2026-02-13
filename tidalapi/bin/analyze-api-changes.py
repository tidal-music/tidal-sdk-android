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


def format_report(old_spec, new_spec, ep_added, ep_removed, s_added, s_removed, s_modified):
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

    old_schemas = get_schemas(old_spec)
    new_schemas = get_schemas(new_spec)
    s_added, s_removed, s_modified = diff_schemas(old_schemas, new_schemas)

    report = format_report(
        old_spec, new_spec, ep_added, ep_removed, s_added, s_removed, s_modified
    )
    print(report)

    has_breaking = bool(ep_removed or s_removed or any(
        any(c[0] in ("removed_field", "type_changed", "became_required") for c in changes)
        for changes in s_modified.values()
    ))
    sys.exit(1 if has_breaking else 0)


if __name__ == "__main__":
    main()
