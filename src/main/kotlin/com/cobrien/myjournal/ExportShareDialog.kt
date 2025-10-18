package com.cobrien.myjournal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.awt.Desktop
import java.io.File
import java.net.URI

@Composable
fun ExportShareDialog(
    entry: JournalEntry,
    onDismiss: () -> Unit,
    onExport: (ExportOptions, ExportAction) -> Unit
) {
    var includeTitle by remember { mutableStateOf(true) }
    var includeDate by remember { mutableStateOf(true) }
    var includeContent by remember { mutableStateOf(true) }
    var showSuccess by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val pdfExporter = remember { PdfExporter() }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Export & Share",
                style = MaterialTheme.typography.headlineSmall
            ) 
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Choose what to include in the PDF:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Include Title")
                            Checkbox(
                                checked = includeTitle,
                                onCheckedChange = { includeTitle = it }
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Include Date")
                            Checkbox(
                                checked = includeDate,
                                onCheckedChange = { includeDate = it }
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Include Content")
                            Checkbox(
                                checked = includeContent,
                                onCheckedChange = { includeContent = it }
                            )
                        }
                    }
                }
                
                Divider()
                
                Text(
                    "Export Options:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                // Save to Downloads button
                Button(
                    onClick = {
                        val options = ExportOptions(includeTitle, includeDate, includeContent)
                        val file = pdfExporter.saveToDownloads(entry, options)
                        
                        if (file != null) {
                            successMessage = "Saved to Downloads:\n${file.name}"
                            showSuccess = true
                        } else {
                            errorMessage = "Failed to save PDF"
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("💾 Save to Downloads")
                }
                
                // Email button
                Button(
                    onClick = {
                        val options = ExportOptions(includeTitle, includeDate, includeContent)
                        val file = pdfExporter.saveToDownloads(entry, options)
                        
                        if (file != null) {
                            val success = openEmailWithAttachment(file, entry.title)
                            if (success) {
                                successMessage = "Opening email client..."
                                showSuccess = true
                            } else {
                                errorMessage = "Could not open email client.\nPDF saved to: ${file.absolutePath}"
                                showError = true
                            }
                        } else {
                            errorMessage = "Failed to create PDF"
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("📧 Email PDF")
                }
                
                Text(
                    "Note: Email will open your default email application",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
    
    // Success dialog
    if (showSuccess) {
        AlertDialog(
            onDismissRequest = { 
                showSuccess = false
                onDismiss()
            },
            title = { Text("Success!") },
            text = { Text(successMessage) },
            confirmButton = {
                Button(onClick = { 
                    showSuccess = false
                    onDismiss()
                }) {
                    Text("OK")
                }
            }
        )
    }
    
    // Error dialog
    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        )
    }
}

fun openEmailWithAttachment(file: File, subject: String): Boolean {
    return try {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                val mailto = "mailto:?subject=${URI("", subject, "").rawSchemeSpecificPart}&body=Please find attached my journal entry."
                desktop.mail(URI(mailto))
                
                // Note: Desktop.mail() doesn't support attachments directly
                // The PDF is saved to Downloads folder for manual attachment
                true
            } else {
                // Try xdg-email on Linux
                val process = ProcessBuilder(
                    "xdg-email",
                    "--subject", subject,
                    "--body", "Please find attached: ${file.name}\nLocation: ${file.absolutePath}",
                    "--attach", file.absolutePath
                ).start()
                
                process.waitFor()
                process.exitValue() == 0
            }
        } else {
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
