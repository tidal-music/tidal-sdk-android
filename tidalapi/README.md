# Module tidalapi

This module provides easy access to the TIDAL Open API. Read more about it [here](https://developer.tidal.com/apiref?spec=catalogue-v2&ref=get-albums-v2&at=THIRD_PARTY).

## Prerequisites

To effectively use this module, you will need to be able to provide a `CredentialsProvider` instance
from the `auth` module. This is necessary to authenticate with the TIDAL API.

Instantiate your `TidalApiClient` like this:
```kotlin
val client = TidalApiClient(credentialsProvider)
```
This client exposes the various API endpoints available as `Retrofit` service instances. You can
then call the methods on these services to interact with the TIDAL API.

## API Code Regeneration

The API client code is generated from the TIDAL OpenAPI specifications. At this time, generation has to be triggered manually.
### Use Github Actions:
1. Run [Regenerate TidalApi Code](https://github.com/tidal-music/tidal-sdk-android/actions/workflows/regenerate_tidalapi_module.yml)
2. This action will create a pull request. Test and verify the changes before merging!

### Run locally
#### Prerequisites

- Python 3.x
- Java Runtime Environment (JRE)

#### Generate API Code

1. Navigate to the `bin` directory:
   ```bash
   cd bin
   ```

2. Run the generation script:
   ```bash
   ./generate-api.sh
   ```

The script will:
1. Set up a Python virtual environment
2. Install required dependencies
3. Download the latest OpenAPI specifications from TIDAL
4. Generate the API client code
5. Clean up temporary files

The generated code will be placed in `src/main/kotlin/com/tidal/sdk/tidalapi/generated/`.

#### Manual Generation

If you need more control over the generation process, refer to the detailed instructions in `bin/README.md`.

## API Structure

The generated API code follows TIDAL's API structure and includes:
- Models for all API entities (albums, tracks, artists, etc.)
- API endpoints for each service
- Type-safe request/response handling
- Kotlin coroutines support for asynchronous operations
