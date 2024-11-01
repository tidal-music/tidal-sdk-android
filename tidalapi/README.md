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
