# TIDAL Android SDK 

This is the repository for TIDAL Android SDK modules.

## Working in this repository

### First time setup
It is strongly recommended to run `local-setup.sh` right after cloning the repository. This will install the pre-commit git hook to run lint checks for your code. CI will also run these checks, but it's best to prevent CI failures by running the checks locally.
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

2. Open a Pull Request with your version bump, get it approved and merge it. A release draft will be created for the module you changed.

3. Find your draft in the [releases list](https://github.com/tidal-music/tidal-sdk-android/releases) and add some meaningful sentences about the release, changelog style (Note: This paragraph is temporary, as we will automate and regulate changelog creation, but for now, you are free to type)

4. Check in with your teammates, lead, the module's owner etc. to make sure the release is ready to go.

5. Click `Publish` at the bottom of your draft release. This will trigger a workflow to publish a package of the new version

6. Find your newly published package [here](https://github.com/orgs/tidal-music/packages?repo_name=tidal-sdk-android). 
