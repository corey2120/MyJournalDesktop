# My Journal Desktop - Distribution Packages

I've created multiple distribution formats for your My Journal application!

## 📦 Available Packages

### 1. AppImage (Recommended - Most Portable)
**File**: `MyJournal-1.0.0-x86_64.AppImage` (102 MB)

**Installation**:
```bash
chmod +x MyJournal-1.0.0-x86_64.AppImage
./MyJournal-1.0.0-x86_64.AppImage
```

**Features**:
- ✅ Works on any Linux distribution
- ✅ No installation required
- ✅ Self-contained (includes Java runtime)
- ✅ Can be run from anywhere
- ✅ Integrates with application menu

**Optional**: Make it available system-wide:
```bash
mkdir -p ~/.local/bin
mv MyJournal-1.0.0-x86_64.AppImage ~/.local/bin/myjournal
```

### 2. Tarball with Installer
**File**: `MyJournal-1.0.0-linux.tar.gz` (101 MB)

**Installation**:
```bash
tar -xzf MyJournal-1.0.0-linux.tar.gz
cd app
./install.sh
```

This installs to `~/.local/share/MyJournal/` and creates a launcher in `~/.local/bin/`

**Uninstall**:
```bash
~/.local/share/MyJournal/uninstall.sh
```

### 3. Direct Run (Development)
```bash
cd /home/cobrien/MyJournalDesktop
./gradlew run
```

## 🚀 Quick Start

**Easiest Option - AppImage**:
```bash
cd /home/cobrien/MyJournalDesktop
./MyJournal-1.0.0-x86_64.AppImage
```

**Install System-Wide - Tarball**:
```bash
cd /home/cobrien/MyJournalDesktop
tar -xzf MyJournal-1.0.0-linux.tar.gz
cd app
./install.sh
# Then run: myjournal
```

## 📝 Features

- Create and edit journal entries
- Customizable colors (5 backgrounds, 5 text colors)
- Adjustable font sizes (12-24sp)
- Settings with default preferences
- Persistent local storage
- Material 3 UI design
- Full keyboard navigation

## 💾 Data Storage

Your journal entries are stored in: `~/.myjournal/`
- `entries.json` - Your journal entries
- `preferences.json` - Your settings

## 🔧 System Requirements

- **OS**: Linux (64-bit)
- **Display**: X11 or Wayland
- **Memory**: 256 MB RAM minimum
- **Storage**: 150 MB for application

## 📤 Sharing

You can share either file with others:
- **AppImage**: Just send the `.AppImage` file
- **Tarball**: Send the `.tar.gz` file with `INSTALL.md`

## 🐛 Troubleshooting

### AppImage won't run
Make it executable:
```bash
chmod +x MyJournal-1.0.0-x86_64.AppImage
```

### Application crashes on start
The non-Flatpak versions (AppImage and Tarball) work best.
Run directly for testing:
```bash
cd /home/cobrien/MyJournalDesktop
./gradlew run
```

### Can't find the app after installation
Make sure `~/.local/bin` is in your PATH:
```bash
echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

## 📄 Files in This Directory

```
MyJournalDesktop/
├── MyJournal-1.0.0-x86_64.AppImage    # AppImage (ready to run)
├── MyJournal-1.0.0-linux.tar.gz       # Tarball with installer
├── DISTRIBUTION.md                     # This file
├── INSTALL.md                          # Installation guide
├── README.md                           # Project documentation
├── build.sh                            # Build helper script
└── src/                                # Source code
```

## 🎯 Next Steps

1. **Test the AppImage**:
   ```bash
   ./MyJournal-1.0.0-x86_64.AppImage
   ```

2. **Or install system-wide**:
   ```bash
   tar -xzf MyJournal-1.0.0-linux.tar.gz
   cd app && ./install.sh
   ```

3. **Create your first journal entry!**

## 📧 Notes

- Both packages work on Fedora Workstation 42 and other Linux distributions
- The AppImage is the most portable and easiest to share
- The Tarball installer integrates better with the system
- No sudo/root access required for either option

Enjoy journaling! 📝✨
