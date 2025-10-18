# My Journal Desktop - Version 1.1.0 Updates

## 📤 PDF Export & Email Support Added!

### What's New in v1.1.0:

#### 📄 PDF Export
- **Save to Downloads** - Export your journal entries as beautiful PDF documents
- **Customizable Options:**
  - Include/exclude title
  - Include/exclude date
  - Include/exclude content
- **Smart naming** - PDFs are named with date and entry title
- **Professional formatting** - Clean, readable PDF output

#### 📧 Email Integration
- **Email PDF directly** from the app
- **Automatic email client launch** with your default mail app
- **Pre-filled subject line** with entry title
- **Works on Linux** using xdg-email or Desktop API
- **Fallback options** if email client isn't available

### How to Use PDF Export:

1. **Open any journal entry**
2. **Click the Share (📤) icon** in the top bar
3. **Choose what to include:**
   - ✓ Title
   - ✓ Date
   - ✓ Content
4. **Select an option:**
   - **💾 Save to Downloads** - Saves PDF to ~/Downloads
   - **📧 Email PDF** - Opens your email client with PDF attached

### Features:

**PDF Export:**
- High-quality PDF generation using iText7
- Preserves your chosen fonts and formatting
- Title in bold 20pt
- Date in italic 12pt
- Content in your selected font size
- Saved to ~/Downloads folder
- Filename format: `YYYY-MM-DD_EntryTitle.pdf`

**Email Support:**
- Works with any email client (Thunderbird, Evolution, etc.)
- Attaches PDF automatically on Linux
- Pre-fills subject with entry title
- Fallback to manual attachment if needed
- Shows clear success/error messages

### Technical Details:

**New Files:**
- `PdfExporter.kt` - PDF generation using iText7
- `ExportShareDialog.kt` - Export UI with options

**Dependencies:**
- iText7 PDF library (already included)
- Java Desktop API for email
- xdg-email for Linux email integration

### Installation:

**AppImage v1.1.0:**
```bash
chmod +x MyJournal-1.1.0-x86_64.AppImage
./MyJournal-1.1.0-x86_64.AppImage
```

**Tarball v1.1.0:**
```bash
tar -xzf MyJournal-1.1.0-linux.tar.gz
cd app && ./install.sh
```

### Examples:

**Export to PDF:**
1. Write your journal entry
2. Click Share icon
3. Click "Save to Downloads"
4. Check ~/Downloads for your PDF!

**Email Entry:**
1. Click Share icon on any entry
2. Choose export options
3. Click "Email PDF"
4. Your email client opens with PDF ready to send!

### Notes:

- PDFs are automatically saved to ~/Downloads
- Email feature requires a configured email client
- On Linux, uses xdg-email for best compatibility
- All your journal formatting is preserved in PDF
- Works offline - no internet needed for PDF creation

### What's Included from v1.0.1:

✅ Streamlined writing experience
✅ Beautiful themed colors (Cream, Mint, Sky, Rose, Lavender)
✅ Save dialog for naming entries
✅ Enhanced customization dialog
✅ **NEW: PDF Export**
✅ **NEW: Email Integration**

### Coming Soon:

⏳ Handwriting/drawing canvas
⏳ Cloud sync options
⏳ Search functionality
⏳ Tags and categories

Enjoy sharing your journal entries! 📝✨📤
