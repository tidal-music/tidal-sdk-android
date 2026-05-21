# Simple Music Player

A simple, easy-to-use music player app that streams music from TIDAL using your account.

## Features

- 🎵 Stream music from TIDAL catalog
- 🔐 Secure OAuth login with your TIDAL account
- ▶️ Simple playback controls (Play/Pause)
- 📱 Clean, modern Material Design 3 UI
- 🎯 Play any track by entering its TIDAL ID

## Quick Start

**See [SETUP.md](SETUP.md) for complete setup instructions!**

### TL;DR

1. Get TIDAL API credentials from https://developer.tidal.com/
2. Register redirect URI: `simplemusicplayer://auth/callback`
3. Add credentials to `local.properties`:
   ```properties
   tidal.clientid=YOUR_CLIENT_ID
   tidal.clientsecret=YOUR_CLIENT_SECRET
   ```
4. Run the app in Android Studio
5. Login and play music!

## How It Works

This app demonstrates:
- **TIDAL Auth SDK**: OAuth 2.0 authentication flow
- **TIDAL Player SDK**: High-quality music streaming
- **Deep Linking**: Handling OAuth redirect URIs
- **Jetpack Compose**: Modern Android UI
- **MVVM Architecture**: Clean separation of concerns

## What is the Redirect URI?

The **redirect URI** (`simplemusicplayer://auth/callback`) is where TIDAL sends users after they log in successfully.

Think of it like this:
1. Your app sends user to TIDAL's login page
2. User logs in on TIDAL's website
3. TIDAL redirects back to your app using this URI
4. App receives the login confirmation
5. User is logged in!

The app is already configured to handle this URI - you just need to register it in your TIDAL Developer Portal.

## Project Structure

```
simple-player/
├── SETUP.md                    # Detailed setup guide (READ THIS FIRST!)
├── README.md                   # This file
├── build.gradle.kts           # Build configuration
└── src/main/
    ├── AndroidManifest.xml    # App manifest with deep link config
    └── kotlin/com/simple/musicplayer/
        ├── MusicPlayerApp.kt          # Application class
        ├── MainActivity.kt            # Main UI
        └── MusicPlayerViewModel.kt    # Business logic
```

## Requirements

- Android Studio Hedgehog or newer
- Android device/emulator with API 24+ (Android 7.0+)
- TIDAL Developer account (free)
- Active TIDAL subscription (for full-length playback)

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Architecture**: MVVM with ViewModel
- **Async**: Kotlin Coroutines + Flow
- **Audio**: TIDAL Player SDK (ExoPlayer)
- **Auth**: TIDAL Auth SDK (OAuth 2.0)

## Need Help?

Check [SETUP.md](SETUP.md) for:
- Step-by-step setup instructions
- Troubleshooting common issues
- Understanding how OAuth works
- Extending the app with new features

## License

This is a demonstration app built on top of the TIDAL Android SDK.
See the main project LICENSE file for details.

---

**Ready to start? Open [SETUP.md](SETUP.md) and follow the steps!** 🚀
