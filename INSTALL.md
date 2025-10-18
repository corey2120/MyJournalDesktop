# My Journal - Installation Package

## Installation

### Quick Install (Recommended)
```bash
tar -xzf MyJournal-1.0.0-linux.tar.gz
cd app
./install.sh
```

This will install My Journal to `~/.local/share/MyJournal/`

### Manual Run (No Installation)
```bash
tar -xzf MyJournal-1.0.0-linux.tar.gz
cd app/MyJournal/bin
./MyJournal
```

## Usage

After installation:
- Run from terminal: `myjournal`
- Or search for "My Journal" in your application menu

## Data Storage

Your journal entries are stored in: `~/.myjournal/`

## Uninstall

```bash
~/.local/share/MyJournal/uninstall.sh
```

## System Requirements

- Linux (64-bit)
- Java 17+ (bundled)
- X11 or Wayland display server

## Features

- Create and edit journal entries
- Customizable colors and fonts
- Settings with default preferences
- Persistent local storage
- Material 3 UI design

## License

Created for personal use.
