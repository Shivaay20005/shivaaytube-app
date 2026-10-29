<div align="center">

# ShivaayTube

**A privacy-respecting YouTube and YouTube Music client for Android, with a recommendation engine that stays on your phone.**

Built with Jetpack Compose & Material 3 · Android 8.0+

[![Download](https://img.shields.io/badge/download-latest_release-orange)](https://github.com/Shivaay20005/shivaaytube-app/releases/latest)
[![Nightly](https://img.shields.io/badge/try-nightly_build-yellow)](https://github.com/Shivaay20005/shivaaytube-app/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/license-GPL--3.0-blue)](License)
[![Platform](https://img.shields.io/badge/platform-Android_8.0%2B-green)](https://github.com/Shivaay20005/shivaaytube-app)

</div>

---

## About

ShivaayTube is an Android video & music client that plays YouTube content without accounts, ads or tracking. It ships **ShivaayNeuro**, a recommendation engine that runs **100% on your device** — it learns what you like from your watch behaviour, and none of that data ever leaves your phone. You can inspect everything it knows, adjust it, or wipe it at any time.

## Features

### Video
- High-quality playback via ExoPlayer (Media3) with resolution switching (1080p, 720p, 480p, 360p)
- SponsorBlock — automatically skips sponsors, intros, outros and filler
- DeArrow — replaces clickbait thumbnails and titles with community-sourced alternatives
- Return YouTube Dislike (RYD)
- Background playback — listen with the screen off
- Picture-in-Picture — keep watching while using other apps
- Casting to smart TVs and streaming devices
- Playback speed control (0.25x to 2x)
- Video chapters with seek jumping
- Gesture controls for brightness, volume and seeking
- Subtitles with customisable font size, colour and background
- Downloads with VP9, AV1 and standard format support
- Resume playback from where you left off

### Music
- Dedicated music player with album art and audio visualisations
- Queue management with add, remove and reorder
- Shuffle and repeat (single/all)
- Persistent mini player across the app
- Synchronised lyrics display
- Fetches tracks from YouTube Music

### Recommendations (ShivaayNeuro engine)
- Runs entirely on-device — no server, no telemetry, no account needed
- Learns from what you watch, skip, like, dislike, search for and how long you watch
- Distinguishes weekday/weekend and morning/night patterns
- Detects when you're getting bored of a topic and mixes in fresh content
- Keeps your feed from collapsing into the same 2–3 topics

### Extras
- Full Material 3 Expressive UI with real tablet and foldable layouts, plus an Android TV (Leanback) interface
- Offline downloads, local media playback, custom themes and alternate icon styles
- Device-to-device sync via QR pairing
- Home-screen widgets
- Flavours: `github` (in-app updater) and `foss` (no updater)

## Download

Grab the latest APK from the [Releases page](https://github.com/Shivaay20005/shivaaytube-app/releases/latest).
Nightly builds are produced automatically from `main` by the CI workflow and install alongside the stable app.

## Build

```bash
git clone https://github.com/Shivaay20005/shivaaytube-app.git
cd shivaaytube-app
./gradlew assembleGithubRelease   # or: assembleFossRelease
```

## Contact

- **GitHub:** [Shivaay20005](https://github.com/Shivaay20005)
- **Telegram:** [Shivaay20005](https://t.me/Shivaay20005)
- **Instagram:** [bhole_lover_shivaay_](https://www.instagram.com/bhole_lover_shivaay_)

## License

ShivaayTube is released under the [GNU GPL v3](License). You are free to use, study, modify and share it under the terms of that license.
