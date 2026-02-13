#!/usr/bin/env python3
"""Generate a deterministic, human-readable API surface snapshot from an OpenAPI spec."""

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
        parts = [schema_type_str(v) for v in variants]
        return " | ".join(parts)
    t = schema.get("type", "any")
    if t == "array":
        items = schema_type_str(schema.get("items", {}))
        return f"array[{items}]"
    if "enum" in schema:
        vals = ", ".join(str(v) for v in schema["enum"])
        return f"enum[{vals}]"
    fmt = schema.get("format")
    if fmt:
        return f"{t}({fmt})"
    return t


def extract_return_type(responses):
    """Extract the success (2xx) return type from responses."""
    for code in sorted(responses.keys()):
        if code.startswith("2"):
            resp = responses[code]
            content = resp.get("content", {})
            for media, media_obj in content.items():
                s = media_obj.get("schema", {})
                return schema_type_str(s)
            return "void"
    return "void"


def generate_snapshot(spec):
    lines = []
    version = spec.get("info", {}).get("version", "unknown")
    paths = spec.get("paths", {})
    schemas = spec.get("components", {}).get("schemas", {})

    # Count endpoints
    endpoint_count = sum(
        len([m for m in path_obj if m in ("get", "post", "put", "patch", "delete")])
        for path_obj in paths.values()
    )

    lines.append(f"# TIDAL API Surface Snapshot")
    lines.append(
        f"# Spec version: {version} | Endpoints: {endpoint_count} | Models: {len(schemas)}"
    )
    lines.append("")
    lines.append("## Endpoints")
    lines.append("")

    # Collect all endpoints sorted by path then method
    method_order = {"get": 0, "post": 1, "put": 2, "patch": 3, "delete": 4}
    endpoints = []
    for path, path_obj in paths.items():
        for method in path_obj:
            if method not in method_order:
                continue
            endpoints.append((path, method, path_obj[method]))

    endpoints.sort(key=lambda e: (e[0], method_order.get(e[1], 99)))

    for path, method, op in endpoints:
        lines.append(f"{method.upper()} {path}")

        # Parameters
        params = op.get("parameters", [])
        if params:
            parts = []
            for p in sorted(params, key=lambda x: x.get("name", "")):
                name = p.get("name", "?")
                required = p.get("required", False)
                ptype = schema_type_str(p.get("schema", {}))
                suffix = "" if required else "?"
                parts.append(f"{name}{suffix}: {ptype}")
            lines.append(f"  params: {', '.join(parts)}")

        # Return type
        responses = op.get("responses", {})
        ret = extract_return_type(responses)
        lines.append(f"  returns: {ret}")
        lines.append("")

    lines.append("## Models")
    lines.append("")

    for name in sorted(schemas.keys()):
        schema = schemas[name]
        lines.append(name)

        required_fields = set(schema.get("required", []))
        props = schema.get("properties", {})

        # Sort: required first (alphabetical), then optional (alphabetical)
        req_props = sorted([k for k in props if k in required_fields])
        opt_props = sorted([k for k in props if k not in required_fields])

        for prop_name in req_props + opt_props:
            prop = props[prop_name]
            ptype = schema_type_str(prop)
            req_str = "required" if prop_name in required_fields else "optional"
            lines.append(f"  {prop_name}: {ptype} ({req_str})")

        # Handle allOf / oneOf / anyOf at schema level (no properties)
        if not props:
            for combo in ("allOf", "oneOf", "anyOf"):
                if combo in schema:
                    refs = [schema_type_str(s) for s in schema[combo]]
                    lines.append(f"  {combo}: {', '.join(refs)}")

        lines.append("")

    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(
        description="Generate API surface snapshot from OpenAPI spec"
    )
    parser.add_argument("--spec", required=True, help="Path to OpenAPI JSON spec")
    parser.add_argument(
        "--output",
        help="Output file path (stdout if not specified)",
    )
    args = parser.parse_args()

    with open(args.spec, "r") as f:
        spec = json.load(f)

    snapshot = generate_snapshot(spec)

    # Ensure consistent trailing newline
    if not snapshot.endswith("\n"):
        snapshot += "\n"

    if args.output:
        with open(args.output, "w") as f:
            f.write(snapshot)
        print(f"Snapshot written to {args.output}", file=sys.stderr)
    else:
        sys.stdout.write(snapshot)


if __name__ == "__main__":
    main()
