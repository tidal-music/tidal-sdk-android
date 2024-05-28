# Module auth

Auth module handles Authentication and Authorization when interacting with the TIDAL API or other TIDAL SDK modules.
It provides easy to use authentication to interact with TIDAL's oAuth2 server endpoints.

## Features
* User Login and Sign up handling (through login.tidal.com)
* Automatic session refresh (refreshing oAuthTokens)
* Secure and encrypted storage of your tokens

## Documentation
* Read the [documentation](https://github.com/tidal-music/tidal-sdk/blob/main/Auth.md) for a detailed overview of the auth functionality.
* Check the [API documentation](https://tidal-music.github.io/tidal-sdk-android/auth/index.html) for the module classes and methods.
* Visit our [TIDAL Developer Platform](https://developer.tidal.com/) for more information and getting started. 

## Usage

### Installation
Add the dependency to your `build.gradle.kts` file.
```kotlin
dependencies {
    implementation("com.tidal.sdk:auth:<VERSION>")
}
```

### Client Credentials

This authentication method uses `clientId` and `clientSecret`, e.g. when utilizing the [TIDAL API](https://developer.tidal.com/documentation/api/api-overview). Follow these steps in order to get an oAuth token.

1. Initiate the process by initialising [TidalAuth](https://github.com/tidal-music/tidal-sdk-android/blob/main/auth/src/main/kotlin/com/tidal/sdk/auth/TidalAuth.kt) by providing an [AuthConfig](https://github.com/tidal-music/tidal-sdk-android/blob/main/auth/src/main/kotlin/com/tidal/sdk/auth/model/AuthConfig.kt) with `clientId` and `clientSecret`.
```kotlin
    val authConfig = AuthConfig(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET",
        credentialsKey = "storage",
        enableCertificatePinning = true,
        logLevel = NetworkLogLevel.BODY,
    )

    val tidalAuth = TidalAuth.getInstance(authConfig, context)
```   
2. Get access to the [CredentialsProvider](https://github.com/tidal-music/tidal-sdk-android/blob/main/auth/src/main/kotlin/com/tidal/sdk/auth/CredentialsProvider.kt) which is responsible for getting [Credentials](https://github.com/tidal-music/tidal-sdk-android/blob/main/auth/src/main/kotlin/com/tidal/sdk/auth/model/Credentials.kt) and any updates sent through a message bus.
```kotlin
    val credentialsProvider = tidalAuth.credentialsProvider
```  
   
3. Obtain credentials by calling `credentialsProvider.getCredentials`, which when successfully executed, returns credentials containing a `token`.
```kotlin
    suspend fun getTidalToken(): String? {
        val result = credentialsProvider.getCredentials()
        return result.successData?.token
    }
```  
  
4. Make API calls to your desired endpoint and include `Authentication: Bearer YOUR_TOKEN` as a header.
5. _(Optional)_ Listen to credentials update messages.
```kotlin
    suspend fun logCredentialsUpdates() {
        credentialsProvider.bus.collectLatest {
            Log.d(TAG, "message=$it")
        }
    }
``` 


### Authorization Code Flow (user login)
(Only available for TIDAL internally developed applications for now)

To implement the login redirect flow, follow these steps or refer to our [Demo app](https://github.com/tidal-music/tidal-sdk-android/tree/main/auth/apps/demo) implementation.

1. Initiate the process by initialising [Auth](./auth/src/main/kotlin/com/tidal/sdk/auth/TidalAuth.
   kt).
2. For the first login:
    * Acquire `Auth` from your `TidalAuth` instance. Call its `initializeLogin` function, which 
      returns the login URL. Open this URL in a webview, where the user can log in using their username/password.
    * A successful login will return a `RedirectUri`.
    * After redirection to your app, follow up with a call to `finalizeLogin`, passing in the returned `RedirectUri`.
    * Once logged in, you can use `credentialsProvider.getCredentials` to obtain `Credentials` for activities like API calls.
3. For subsequent logins, when the user returns to your app, simply call `credentialsProvider.getCredentials`. This is sufficient unless the user actively logs out or a token is revoked (e.g., due to a password change).

> ⚠️ Ensure to invoke `credentialsProvider.getCredentials` each time you need a token and avoid storing it. This approach enables the SDK to manage timeouts, upgrades, or automatic retries seamlessly.

### Device Login
(Only available for TIDAL internally developed applications for now)

For devices with limited input capabilities, such as TVs, an alternative login method is provided. Follow these steps or refer to our [Demo app](https://github.com/tidal-music/tidal-sdk-android/tree/main/auth/apps/demo) implementation.

1. Initiate the process by initialising [TidalAuth](.
   /auth/src/main/kotlin/com/tidal/sdk/auth/TidalAuth.kt).
2. Use `initializeDeviceLogin` and await the response.
3. The response will contain a `userCode` and a `verificationUri`; display these to the user.
4. Instruct the user to visit `link.tidal.com`, log in, and enter the displayed code.
5. Subsequently, call `finalizeDeviceLogin`, which will continually poll the backend until the user successfully enters the code. Upon a successful promise return, you are ready to proceed.
6. Retrieve a token by calling `.credentialsProvider.getCredentials`.

> 💡 Many modern apps feature a QR-Code for scanning, which you can also generate. Ensure it includes `verificationUriComplete`, as provided in the response.
