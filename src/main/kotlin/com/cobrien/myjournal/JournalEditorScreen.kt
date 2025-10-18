package com.cobrien.myjournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEditorScreen(
    entry: JournalEntry?,
    onBack: () -> Unit,
    onSave: (JournalEntry) -> Unit
) {
    if (entry == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    
    var title by remember { mutableStateOf(entry.title) }
    var content by remember { mutableStateOf(entry.content) }
    var backgroundColor by remember { mutableStateOf(entry.backgroundColor.toComposeColor()) }
    var textColor by remember { mutableStateOf(entry.textColor.toComposeColor()) }
    var fontSize by remember { mutableStateOf(entry.fontSize) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    
    val timestamp = remember { System.currentTimeMillis() }
    val dateFormat = remember { SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Write", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showColorPicker = true }) {
                        Icon(Icons.Default.Palette, "Customize Colors")
                    }
                    IconButton(onClick = { showSaveDialog = true }) {
                        Icon(Icons.Default.Done, "Save")
                    }
                    IconButton(onClick = { showExportDialog = true }) {
                        Icon(Icons.Default.Share, "Export & Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Date display
                Text(
                    text = dateFormat.format(Date(timestamp)),
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Full writing experience - no title field, just content
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { 
                        Text(
                            "Start writing your thoughts...\n\nWhat's on your mind today?\nHow are you feeling?\nWhat happened?\nWhat are you grateful for?\n\nWrite freely, you'll name this entry when you're done.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor.copy(alpha = 0.4f),
                            lineHeight = (fontSize * 1.6).sp
                        ) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 400.dp),
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = fontSize.sp,
                        lineHeight = (fontSize * 1.6).sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = textColor,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
    
    // Save dialog for naming the entry
    if (showSaveDialog) {
        var dialogTitle by remember { mutableStateOf(title) }
        
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { 
                Text(
                    if (entry.title != "Untitled") "Rename Entry" else "Name Your Entry",
                    style = MaterialTheme.typography.headlineSmall
                ) 
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        if (entry.title != "Untitled") 
                            "Edit the title for this journal entry:" 
                        else 
                            "Give this journal entry a memorable title:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        value = dialogTitle,
                        onValueChange = { dialogTitle = it },
                        label = { Text("Title") },
                        placeholder = { Text("e.g., A Wonderful Day") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Leave blank for \"Untitled\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSave(entry.copy(
                            title = dialogTitle.ifBlank { "Untitled" },
                            content = content,
                            backgroundColor = backgroundColor.toLongValue(),
                            textColor = textColor.toLongValue(),
                            fontSize = fontSize
                        ))
                        showSaveDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Export & Share dialog
    if (showExportDialog) {
        ExportShareDialog(
            entry = entry,
            onDismiss = { showExportDialog = false },
            onExport = { _, _ ->
                showExportDialog = false
            }
        )
    }
    
    if (showColorPicker) {
        CustomizationDialog(
            backgroundColor = backgroundColor,
            textColor = textColor,
            fontSize = fontSize,
            onBackgroundColorChange = { backgroundColor = it },
            onTextColorChange = { textColor = it },
            onFontSizeChange = { fontSize = it },
            onDismiss = { showColorPicker = false }
        )
    }
}

@Composable
fun CustomizationDialog(
    backgroundColor: Color,
    textColor: Color,
    fontSize: Int,
    onBackgroundColorChange: (Color) -> Unit,
    onTextColorChange: (Color) -> Unit,
    onFontSizeChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Customize Your Journal",
                style = MaterialTheme.typography.headlineSmall
            ) 
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Background Theme Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Background Theme",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val bgColors = listOf(
                            Color(0xFFFFF8E1) to "Cream",
                            Color(0xFFE8F5E9) to "Mint",
                            Color(0xFFE3F2FD) to "Sky",
                            Color(0xFFFCE4EC) to "Rose",
                            Color(0xFFF3E5F5) to "Lavender"
                        )
                        bgColors.forEach { (color, name) ->
                            Surface(
                                modifier = Modifier
                                    .size(56.dp)
                                    .weight(1f),
                                color = color,
                                shape = MaterialTheme.shapes.medium,
                                border = if (backgroundColor == color) 
                                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                                else 
                                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                                onClick = { onBackgroundColorChange(color) }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (backgroundColor == color) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = name,
                                            tint = Color(0xFF5D4037),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    // Theme names
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Cream", "Mint", "Sky", "Rose", "Lavender").forEach { name ->
                            Text(
                                name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
                
                Divider()
                
                // Text Color Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Text Color",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val textColors = listOf(
                            Color(0xFF5D4037) to "Brown",
                            Color(0xFF424242) to "Gray",
                            Color(0xFF1976D2) to "Blue",
                            Color(0xFF388E3C) to "Green",
                            Color(0xFF6A1B9A) to "Purple"
                        )
                        textColors.forEach { (color, name) ->
                            Surface(
                                modifier = Modifier
                                    .size(48.dp)
                                    .weight(1f),
                                color = color,
                                shape = MaterialTheme.shapes.medium,
                                border = if (textColor == color) 
                                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                                else 
                                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                                onClick = { onTextColorChange(color) }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (textColor == color) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = name,
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                Divider()
                
                // Font Size Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Font Size: ${fontSize}sp",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Slider(
                        value = fontSize.toFloat(),
                        onValueChange = { onFontSizeChange(it.toInt()) },
                        valueRange = 12f..24f,
                        steps = 11,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Preview: The quick brown fox jumps over the lazy dog",
                        fontSize = fontSize.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}
