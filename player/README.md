# Module player
The TIDAL Player client for Android.

The Player module encapsulates the playback functionality of TIDAL media products. The implementation depends on a fork of AndroidX Media repository and uses `ExoPlayer` as the media player.

## Features
* Streaming and playing TIDAL catalog content.
* Core playback functionality.
* Media streaming, caching and error handling. 
* Automatic management of playback session event reporting.
 
## Documentation

* Read the [documentation](https://github.com/tidal-music/tidal-sdk/blob/main/Player.md) for a detailed overview of the player functionality.
* Check the [API documentation](https://tidal-music.github.io/tidal-sdk-android/player/index.html) for the module classes and methods.
* Visit our [TIDAL Developer Platform](https://developer.tidal.com/) for more information and getting started. 

## Usage

### Installation

1. We are using the [TrueTime library](https://github.com/instacart/truetime-android) internally, so you need to add the following to your repositories list:
```kotlin
maven {
    url = uri("https://jitpack.io")
}
```

2. Add the dependency to your `build.gradle.kts` file.
```kotlin
dependencies {
    implementation("com.tidal.sdk:player:<VERSION>")
}
```
### Playing a TIDAL track
The Player depends on the [Auth](https://github.com/tidal-music/tidal-sdk-android/blob/main/auth/README.md) and [EventProducer](https://github.com/tidal-music/tidal-sdk-android/tree/main/eventproducer) modules for authentication and event reporting handling. For detailed instructions on how to set them up, please refer to their guide. 

Here's how to setup the Player and play a TIDAL track:

1. Initialise the Player which depends on a `CredentialsProvider` from the Auth module and an `EventSender` from the EventProducer module.
```kotlin
val player = Player(
    application = application,
    credentialsProvider = auth.credentialsProvider,
    eventSender = eventSender
)
```

2. Load and play a `MediaProduct` track.
```kotlin
val mediaProduct = MediaProduct(ProductType.TRACK, "PRODUCT_ID")

player.playbackEngine.load(mediaProduct)
player.playbackEngine.play()
```

3. _(Optional)_ Listen to [player events](https://github.com/tidal-music/tidal-sdk-android/blob/main/player/playback-engine/src/main/kotlin/com/tidal/sdk/player/playbackengine/model/Event.kt).
```kotlin
player.playbackEngine.events.onEach {
    Log.d(TAG, "Event=$it")
}.launchIn(coroutineScope)
```

## Running the test app

The player module includes a [test app](https://github.com/tidal-music/tidal-sdk-android/tree/main/player/app) that demonstrates how to setup the player and showcases its different functionalities.

As a prerequisite for the player to work, the client is required to be authenticated. You can learn more about the authentication flows in the [Auth module](https://github.com/tidal-music/tidal-sdk-android/tree/main/auth). Note that currently only the ```Client Credentials``` flow is publicly supported. This enables the test app to play 30-second tracks previews. Full length playback is only enabled when the client is authenticated through ```Device Login``` or ```Authorization Code Flow```.

In order to run the test app, please declare your client credentials in the top-level ```local.properties``` file:

```
tidal.clientid="YOUR_CLIENT_ID"
tidal.clientsecret="YOUR_CLIENT_SECRET"
```
**Note**: you can obtain the ```client id``` and ```client secret``` after signing up and creating an application in the [TIDAL Developer Platform](https://developer.tidal.com/). 
