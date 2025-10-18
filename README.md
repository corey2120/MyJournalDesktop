# My Journal - Desktop Edition

A beautiful Kotlin Compose Multiplatform desktop application for journaling, adapted from the Android version. Features streamlined writing, themed colors, PDF export, and email sharing.

## Features

- **Streamlined Writing Experience**: Write first, name later - with helpful prompts and date display
- **Beautiful Themed Colors**: 
  - 5 Background themes: Cream, Mint, Sky, Rose, Lavender
  - 5 Text colors: Brown, Gray, Blue, Green, Purple
  - Adjustable font sizes (12-24sp)
  - Customize per entry or set defaults
- **PDF Export & Email**: Save journal entries as PDF or share via email
- **Persistent Storage**: All entries saved locally to `~/.myjournal/`
- **Clean Desktop UI**: Native desktop experience with Material 3 design

## Installation

### AppImage (Recommended)

**Option 1: Using Gear Lever (Easiest)**

[Gear Lever](https://flathub.org/apps/it.mijorus.gearlever) is a fantastic AppImage manager for Linux. It makes managing AppImages simple and integrates them beautifully into your system.

1. **Install Gear Lever** (if not already installed):
   ```bash
   flatpak install flathub it.mijorus.gearlever
   ```

2. **Download My Journal AppImage**:
   - Download `MyJournal-1.1.0-x86_64.AppImage` to your Downloads folder

3. **Open with Gear Lever**:
   - Launch Gear Lever from your application menu
   - Click "Add AppImage" or drag the AppImage into Gear Lever
   - Gear Lever will automatically integrate it into your system menu
   - The app will appear in your applications with the proper icon

4. **Launch anytime**:
   - Find "My Journal" in your application menu
   - Or launch from Gear Lever's interface

**Benefits of using Gear Lever:**
- Automatic desktop integration
- Proper icon and menu entry
- Easy updates and management
- AppImage organization in one place

**Option 2: Manual Run**

Download and run the AppImage directly:

```bash
chmod +x MyJournal-1.1.0-x86_64.AppImage
./MyJournal-1.1.0-x86_64.AppImage
```

**Features:**
- Works on any Linux distribution
- No installation required
- Self-contained (includes Java runtime)
- Just download and run

### Tarball with Installer

Extract and run the installer:

```bash
tar -xzf MyJournal-1.1.0-linux.tar.gz
cd app
./install.sh
```

This installs to `~/.local/share/MyJournal/` and creates a menu entry.

To uninstall:
```bash
~/.local/share/MyJournal/uninstall.sh
```

## Quick Start Guide

1. **Launch the app** (via Gear Lever or directly)
2. **Click the + button** to create a new entry
3. **Start writing** - the date displays automatically, no title needed yet
4. **Customize colors** - Click the palette icon (🎨) to choose themes
5. **Save your entry** - Click the checkmark (✓) and give it a memorable name
6. **Export or share** - Click the share icon (📤) to save as PDF or email

## Key Features Explained

### Writing Experience
- **Write first, name later**: Focus on your thoughts, then give it a title when saving
- **Date display**: Shows the current date automatically
- **Helpful prompts**: Placeholder text suggests writing prompts
- **Comfortable spacing**: 1.6x line height for easy reading

### Themed Colors
Choose from 5 beautiful background themes:
- 🟡 **Cream** - Warm, classic journal feel (default)
- 🟢 **Mint** - Fresh and calming
- 🔵 **Sky** - Light and airy
- 🌸 **Rose** - Soft and gentle
- 💜 **Lavender** - Elegant and peaceful

And 5 text colors:
- 🟤 **Brown** - Warm and readable (default)
- ⚫ **Gray** - Modern and clean
- 🔵 **Blue** - Professional
- 🟢 **Green** - Natural
- 💜 **Purple** - Creative

### PDF Export & Email
- **Save to Downloads**: Creates professional PDFs in ~/Downloads
- **Email directly**: Opens your email client with PDF attached
- **Customizable**: Choose to include title, date, and/or content
- **Smart naming**: Files named as `YYYY-MM-DD_EntryTitle.pdf`

## Building from Source

### Prerequisites

- JDK 11 or higher
- Gradle 8.5 (included via wrapper)

### Run the Application

```bash
./gradlew run
```

### Create Distribution Packages

**AppImage:**
```bash
./gradlew createDistributable
# Then follow the AppImage creation steps in DISTRIBUTION.md
```

**Tarball:**
```bash
./gradlew createDistributable
cd build/compose/binaries/main
tar -czf MyJournal-linux.tar.gz app/
```

## Project Structure

```
MyJournalDesktop/
├── src/main/kotlin/com/cobrien/myjournal/
│   ├── Main.kt                    # Application entry point
│   ├── JournalApp.kt              # Main app composable
│   ├── Models.kt                  # Data models
│   ├── JournalRepository.kt       # Data persistence
│   ├── JournalViewModel.kt        # State management
│   ├── JournalListScreen.kt       # List view UI
│   ├── JournalEditorScreen.kt     # Editor UI
│   ├── SettingsScreen.kt          # Settings UI
│   ├── PdfExporter.kt             # PDF generation
│   └── ExportShareDialog.kt       # Export UI
├── build.gradle.kts               # Build configuration
├── MyJournal-1.1.0-x86_64.AppImage # AppImage package
├── MyJournal-1.1.0-linux.tar.gz   # Tarball package
├── DISTRIBUTION.md                # Distribution guide
├── INSTALL.md                     # Installation guide
├── CHANGELOG-v1.1.0.md            # Version 1.1.0 changes
└── CHANGELOG-v1.0.1.md            # Version 1.0.1 changes
```

## Data Storage

Journal entries are stored as JSON in `~/.myjournal/`:
- `entries.json` - All journal entries
- `preferences.json` - User preferences

When using Gear Lever or the tarball installer, data remains in the same location for consistency.

## What's Included

✅ **Implemented:**
- Streamlined writing interface
- Beautiful themed colors (Cream, Mint, Sky, Rose, Lavender)
- Save dialog for naming entries
- Enhanced customization dialog
- Default preferences in settings
- Date display on entries
- PDF export functionality
- Email sharing

⏳ **Coming Soon:**
- Handwriting/drawing canvas
- Search functionality
- Tags and categories
- Cloud synchronization
- Dark theme

## Version History

**v1.1.0** - PDF Export & Email + New Icon
- Added PDF export to Downloads
- Added email sharing functionality
- Professional journal-themed icon
- Export options dialog

**v1.0.1** - Enhanced UI
- Streamlined writing experience
- Beautiful themed colors
- Save dialog for naming entries
- Improved customization

**v1.0.0** - Initial Release
- Basic journal functionality
- Compose Multiplatform desktop app

## License

This project is created for personal use.

## System Requirements

- **OS**: Linux (64-bit)
- **Display**: X11 or Wayland
- **Memory**: 256 MB RAM minimum
- **Storage**: 150 MB for application
- **Tested on**: Fedora Workstation 42

## Troubleshooting

### AppImage won't run
Make it executable:
```bash
chmod +x MyJournal-1.1.0-x86_64.AppImage
```

### AppImage not appearing in menu
Use Gear Lever for automatic integration:
```bash
flatpak install flathub it.mijorus.gearlever
```
Then drag the AppImage into Gear Lever.

### Build fails with Java not found
Make sure you have JDK 11+ installed:
```bash
java -version
```

### Application won't start
Run directly for testing:
```bash
./gradlew run
```

### PDF export fails
Check that ~/Downloads exists and is writable:
```bash
ls -ld ~/Downloads
```

### Email doesn't work
Make sure you have an email client configured:
- Thunderbird, Evolution, or similar
- Try `xdg-email` from terminal to test

### Can't find installed app
If using tarball installer, make sure `~/.local/bin` is in your PATH:
```bash
echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

## Recommended Setup

For the best experience on Fedora Workstation 42:

1. **Install Gear Lever** for AppImage management
2. **Download My Journal AppImage**
3. **Add to Gear Lever** for menu integration
4. **Configure email client** for sharing functionality
5. **Enjoy journaling!** 📝✨

## Contributing

This is a personal project, but suggestions and improvements are welcome!
