package com.cobrien.myjournal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun JournalApp() {
    val viewModel = remember { JournalViewModel() }
    val entries by viewModel.entries.collectAsState()
    val currentScreen = remember { mutableStateOf<Screen>(Screen.List) }
    val currentEntry by viewModel.currentEntry.collectAsState()
    val preferences by viewModel.userPreferences.collectAsState()
    
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val screen = currentScreen.value) {
                is Screen.List -> {
                    JournalListScreen(
                        entries = entries,
                        onEntryClick = { id ->
                            viewModel.loadEntry(id)
                            currentScreen.value = Screen.Editor(id)
                        },
                        onNewEntry = {
                            viewModel.createNewEntry()
                            currentScreen.value = Screen.Editor("new")
                        },
                        onDeleteEntry = { id ->
                            viewModel.deleteEntry(id)
                        },
                        onRenameEntry = { id, newTitle ->
                            viewModel.renameEntry(id, newTitle)
                        },
                        onSettingsClick = {
                            currentScreen.value = Screen.Settings
                        }
                    )
                }
                is Screen.Editor -> {
                    JournalEditorScreen(
                        entry = currentEntry,
                        onBack = {
                            viewModel.clearCurrentEntry()
                            currentScreen.value = Screen.List
                        },
                        onSave = { entry ->
                            viewModel.saveEntry(entry)
                            currentScreen.value = Screen.List
                        }
                    )
                }
                is Screen.Settings -> {
                    SettingsScreen(
                        preferences = preferences,
                        onPreferencesChange = { newPrefs ->
                            viewModel.updatePreferences(newPrefs)
                        },
                        onBack = {
                            currentScreen.value = Screen.List
                        }
                    )
                }
            }
        }
    }
}

sealed class Screen {
    object List : Screen()
    data class Editor(val entryId: String) : Screen()
    object Settings : Screen()
}
