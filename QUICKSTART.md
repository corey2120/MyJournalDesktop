# Quick Start Guide - My Journal Desktop

## What This Is

This is a desktop Linux version of your Android MyJournal app, built with Kotlin Compose Multiplatform. It's designed to be packaged as a Flatpak for easy distribution on Fedora Workstation 42 and other Linux systems.

## Quick Test Run

Before building the Flatpak, you can test the application directly:

```bash
cd /home/cobrien/MyJournalDesktop
./gradlew run
```

This will compile and launch the application in a desktop window.

## Building the Flatpak (Recommended)

### Step 1: Install Flatpak Tools (if not already installed)

```bash
sudo dnf install flatpak flatpak-builder
```

### Step 2: Add Flathub and Install Runtimes

```bash
# Add Flathub
flatpak remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo

# Install required runtimes
flatpak install -y flathub org.freedesktop.Platform//23.08 org.freedesktop.Sdk//23.08
flatpak install -y flathub org.freedesktop.Sdk.Extension.openjdk17
```

### Step 3: Build the Flatpak

```bash
cd /home/cobrien/MyJournalDesktop
flatpak-builder --force-clean build-dir com.cobrien.MyJournal.yml
```

This will:
- Download all dependencies
- Compile the Kotlin application
- Package it as a Flatpak

### Step 4: Install Locally

```bash
flatpak-builder --user --install --force-clean build-dir com.cobrien.MyJournal.yml
```

### Step 5: Run Your App

```bash
flatpak run com.cobrien.MyJournal
```

Or find it in your application menu under "Office" → "My Journal"

## Distributing Your Flatpak

To create a single file you can share with others:

```bash
# Create a repository
flatpak-builder --repo=repo --force-clean build-dir com.cobrien.MyJournal.yml

# Build a bundle
flatpak build-bundle repo myjournal.flatpak com.cobrien.MyJournal
```

Now you have `myjournal.flatpak` - a single file you can share! Others install it with:

```bash
flatpak install myjournal.flatpak
```

## Features Implemented

✅ Create and edit journal entries
✅ Customizable colors and fonts per entry
✅ Default preferences/settings
✅ Persistent storage (~/.myjournal/)
✅ List view with edit/delete
✅ Rename entries
✅ Chronological sorting

## Features Not Yet Implemented (from Android version)

⏳ Handwriting/drawing canvas (requires desktop touch/stylus support)
⏳ PDF export (planned)
⏳ Share functionality (planned)

## File Locations

- **Application data**: `~/.myjournal/`
  - `entries.json` - Your journal entries
  - `preferences.json` - Your settings
- **Flatpak data**: `~/.var/app/com.cobrien.MyJournal/` (when run as Flatpak)

## Customization

You can edit these files to customize:
- `com.cobrien.MyJournal.desktop` - Application name, icon, categories
- `com.cobrien.MyJournal.metainfo.xml` - App description, screenshots, changelog
- `build.gradle.kts` - Application version, dependencies

## Troubleshooting

**Problem**: Application won't build
**Solution**: Make sure you have Java 11+ installed: `java -version`

**Problem**: Flatpak build fails
**Solution**: Verify all runtimes are installed: `flatpak list | grep freedesktop`

**Problem**: Can't find the app after installing
**Solution**: Logout and login again, or run: `flatpak run com.cobrien.MyJournal`

## Next Steps

1. Test the application with `./gradlew run`
2. Build the Flatpak following steps above
3. Optionally customize the icon (place a PNG at `src/main/resources/icon.png`)
4. Share the `.flatpak` file with others or submit to Flathub!

## Publishing to Flathub (Optional)

To make your app available on Flathub for everyone:
1. Create a GitHub repository with your manifest
2. Submit a PR to https://github.com/flathub/flathub
3. Follow their review process

See: https://docs.flathub.org/docs/for-app-authors/submission/
