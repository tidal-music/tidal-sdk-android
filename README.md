# TIDAL Android SDK 

This is the repository for TIDAL Android SDK modules.

## Available modules

- [auth](./auth/README.md)
- [common](./common/README.md)
- [eventproducer](./eventproducer/README.md)
- [player](./player/README.md)

## Working in this repository

### First time setup
It is strongly recommended to run `local-setup.sh` right after cloning the repository. This will:
- Install the pre-commit git hook to run lint checks for your code. CI will also run these checks, but it's best to prevent CI failures by running the checks locally.
- Ask you to configure Git to ignore formatting commits when using `git blame`, making it easier to see meaningful code changes.

To run a specific module's test app, you might have to create a `local.properties` file in the root of the project, and add values according to that app's requirements.

### Git Blame Configuration
This repository uses a `.git-blame-ignore-revs` file to exclude formatting commits from `git blame` results. This helps developers focus on meaningful code changes rather than formatting changes.
If you skipped this configuration during the initial setup or need to set it up manually, run:
```
git config blame.ignoreRevsFile .git-blame-ignore-revs
```

### Creating a new module
1. Run the `generate-module.sh` script. It will prompt you to enter a module name using [PascalCase](https://pl.wikipedia.org/wiki/PascalCase).
After confirming the name, a new directory will be created with the basic module setup.
2. Commit the generated code and create a pull request.
3. After that pull request is merged, start working on your module.

### Creating a module release
1. Bump your module's version to the desired value in your module's `gradle.properties` file. You'll find an entry looking like this:
    ```
    # Current Version
    version=1.0.0
    ```
    Change `version` to the new value. This follows [Semantic Versioning](https://semver.org/). Also, you cannot downgrade - the CI/CD pipeline will refuse to work with downgrades.
2. Each new release must have a changelog entry for the corresponding version code. So don't forget to add that too.
3. Open a Pull Request with your version bump, get it approved and merge it. A release will be created for te changed module. It will be pushed to MavenCentral automatically. The release description will be pulled from the changelog.
