package com.cobrien.myjournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    preferences: UserPreferences,
    onPreferencesChange: (UserPreferences) -> Unit,
    onBack: () -> Unit
) {
    var bgColor by remember { mutableStateOf(preferences.defaultBackgroundColor.toComposeColor()) }
    var txtColor by remember { mutableStateOf(preferences.defaultTextColor.toComposeColor()) }
    var size by remember { mutableStateOf(preferences.defaultFontSize) }
    var handwriting by remember { mutableStateOf(preferences.enableHandwriting) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = {
                        onPreferencesChange(UserPreferences(
                            defaultBackgroundColor = bgColor.toLongValue(),
                            defaultTextColor = txtColor.toLongValue(),
                            defaultFontSize = size,
                            enableHandwriting = handwriting
                        ))
                        onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Back & Save")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onPreferencesChange(UserPreferences(
                            defaultBackgroundColor = bgColor.toLongValue(),
                            defaultTextColor = txtColor.toLongValue(),
                            defaultFontSize = size,
                            enableHandwriting = handwriting
                        ))
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Default Settings for New Entries",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Divider()
            
            // Background Theme Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                border = if (bgColor == color) 
                                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                                else 
                                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                                onClick = { bgColor = color }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (bgColor == color) {
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
            }
            
            // Text Color Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                border = if (txtColor == color) 
                                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                                else 
                                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                                onClick = { txtColor = color }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (txtColor == color) {
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
            }
            
            // Font Size Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Font Size: ${size}sp",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Slider(
                        value = size.toFloat(),
                        onValueChange = { size = it.toInt() },
                        valueRange = 12f..24f,
                        steps = 11,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Preview: The quick brown fox jumps",
                        fontSize = size.sp
                    )
                }
            }
            
            Divider()
            
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Handwriting Support",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Planned for future updates",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = handwriting,
                        onCheckedChange = { handwriting = it }
                    )
                }
            }
        }
    }
}
