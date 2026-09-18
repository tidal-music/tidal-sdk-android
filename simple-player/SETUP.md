# Simple Music Player - Setup Guide

This is a **simple music player app** that uses your TIDAL account to stream music. This guide will walk you through everything you need to get it running!

## What You'll Be Able to Do

- ✅ Login with your TIDAL account
- ✅ Play any TIDAL track by entering its ID
- ✅ Pause/resume playback
- ✅ Stream high-quality music

---

## Step 1: Get TIDAL Developer Credentials

### 1.1 Sign Up for TIDAL Developer Account

1. Go to **[TIDAL Developer Platform](https://developer.tidal.com/)**
2. Click **"Sign Up"** or **"Get Started"**
3. Create a developer account (it's free!)

### 1.2 Create an Application

1. Once logged in, go to **"My Apps"** or **"Dashboard"**
2. Click **"Create New App"** or **"New Application"**
3. Fill in the details:
   - **App Name**: "My Music Player" (or whatever you want)
   - **Description**: "Personal music player app"

### 1.3 Get Your Credentials

After creating the app, you'll see:
- **Client ID**: A long string like `a1b2c3d4e5f6`
- **Client Secret**: Another long string (keep this private!)

**IMPORTANT**: Copy both of these - you'll need them in the next step!

### 1.4 Configure the Redirect URI

This is the **most important part**:

1. In your TIDAL app settings, find **"Redirect URIs"** or **"OAuth Redirect URIs"**
2. Add this **EXACT** URI:
   ```
   simplemusicplayer://auth/callback
   ```
3. Save the changes

**What is a Redirect URI?**
- When you login, TIDAL's website will redirect you back to your app
- This URI tells TIDAL where to send you after login
- It's like your app's "return address"
- The app is already configured to listen for this exact URI

---

## Step 2: Configure the App

### 2.1 Create or Edit `local.properties`

In the **root directory** of this project (same folder as `build.gradle.kts`), create or edit a file called `local.properties`.

Add these lines (replace with YOUR credentials):

```properties
tidal.clientid=YOUR_CLIENT_ID_HERE
tidal.clientsecret=YOUR_CLIENT_SECRET_HERE
```

**Example:**
```properties
tidal.clientid=a1b2c3d4e5f6g7h8i9j0
tidal.clientsecret=z9y8x7w6v5u4t3s2r1q0p9o8n7m6
```

**⚠️ Important:**
- Do NOT add quotes around the values
- Do NOT commit this file to git (it's already in `.gitignore`)
- Keep your client secret private!

---

## Step 3: Build and Run

### 3.1 Open in Android Studio

1. Open **Android Studio**
2. Click **"Open"** and select this project folder
3. Wait for Gradle sync to complete

### 3.2 Run the App

1. Connect an Android device or start an emulator
2. In Android Studio, select **"simple-player"** from the run configuration dropdown
3. Click the green **"Run"** button ▶️

---

## Step 4: Use the App

### 4.1 First Time Login

1. App opens and shows **"Login with TIDAL"** button
2. Click the button
3. Your browser opens with TIDAL's login page
4. Enter your TIDAL username/password
5. After successful login, you're automatically redirected back to the app
6. You're now logged in!

### 4.2 Play Music

1. Find a TIDAL track ID:
   - Go to https://listen.tidal.com
   - Find any song
   - Look at the URL: `https://listen.tidal.com/track/251380837`
   - The number `251380837` is the track ID

2. In the app:
   - Enter the track ID in the text field
   - Press **"Play"**
   - Music starts playing! 🎵

3. Controls:
   - **Play**: Starts playing the entered track
   - **Pause**: Pauses playback
   - **Logout**: Logs you out

---

## Troubleshooting

### ❌ "Missing TIDAL credentials" error
**Solution**: Make sure you created `local.properties` in the **root** folder (not in `simple-player/`), and it contains your `tidal.clientid` and `tidal.clientsecret`.

### ❌ Browser opens but nothing happens after login
**Solution**:
1. Make sure you added the EXACT redirect URI `simplemusicplayer://auth/callback` in your TIDAL Developer Portal
2. Check that the URI has no typos or extra spaces
3. Try uninstalling and reinstalling the app

### ❌ "Player not initialized" error
**Solution**: Make sure you're logged in first. The player only initializes after successful login.

### ❌ Track won't play
**Possible causes:**
- Invalid track ID (check the number is correct)
- Track not available in your region
- You need an active TIDAL subscription for full playback (without subscription, you might only get 30-second previews)

### ❌ App crashes on startup
**Solution**: Check Android Studio's Logcat for error messages. Common issues:
- Missing dependencies (run Gradle sync)
- TrueTime initialization failed (usually fixes itself after a few seconds)

---

## Understanding the Code

### Key Files:

1. **`MainActivity.kt`**: The main screen and UI
2. **`MusicPlayerViewModel.kt`**: Handles login and music playback logic
3. **`MusicPlayerApp.kt`**: Initializes required libraries
4. **`AndroidManifest.xml`**: Configures the redirect URI deep link

### How Login Works:

```
1. User clicks "Login"
   ↓
2. App opens browser → TIDAL login page
   ↓
3. User enters credentials
   ↓
4. TIDAL validates and redirects to: simplemusicplayer://auth/callback?code=xyz
   ↓
5. Android opens your app (deep link)
   ↓
6. App exchanges code for access token
   ↓
7. User is logged in!
```

### The Redirect URI Explained:

- **Format**: `simplemusicplayer://auth/callback`
- **Parts**:
  - `simplemusicplayer://` - Custom URL scheme (like `https://` but for your app)
  - `auth/callback` - The path

- **Configured in**: `AndroidManifest.xml` (lines 35-47)
- **Used by**: TIDAL to redirect back to your app after login
- **Must match**: The URI you registered in TIDAL Developer Portal

---

## What's Next?

### Ideas to Extend This App:

- Add a search function to find tracks
- Create playlists
- Show album artwork
- Add volume controls
- Display currently playing track info
- Support albums and videos
- Add a queue/playlist functionality

### Customization:

- Change the redirect URI:
  1. Pick a new URI (e.g., `myawesomeapp://callback`)
  2. Update in `build.gradle.kts` (line 27)
  3. Update in `AndroidManifest.xml` (lines 43-46)
  4. Update in TIDAL Developer Portal
  5. Rebuild the app

---

## Need Help?

- **TIDAL SDK Docs**: https://github.com/tidal-music/tidal-sdk-android
- **TIDAL Developer Platform**: https://developer.tidal.com/
- **Questions**: Check the main project README.md

---

## Summary

✅ **You now have a fully functional music player!**

**What you learned:**
- OAuth authentication flow
- How redirect URIs work
- Using the TIDAL SDK
- Building Android apps with Kotlin and Jetpack Compose

**Enjoy your music!** 🎵🎶
