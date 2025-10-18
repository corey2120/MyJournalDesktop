package com.cobrien.myjournal

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JournalViewModel {
    private val repository = JournalRepository()
    
    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    val entries: StateFlow<List<JournalEntry>> = _entries
    
    private val _currentEntry = MutableStateFlow<JournalEntry?>(null)
    val currentEntry: StateFlow<JournalEntry?> = _currentEntry
    
    private val _userPreferences = MutableStateFlow(UserPreferences())
    val userPreferences: StateFlow<UserPreferences> = _userPreferences
    
    init {
        loadEntriesFromRepository()
        loadPreferencesFromRepository()
    }
    
    private fun loadEntriesFromRepository() {
        _entries.value = repository.loadEntries().sortedByDescending { it.modifiedAt }
    }
    
    private fun loadPreferencesFromRepository() {
        _userPreferences.value = repository.loadPreferences()
    }
    
    fun loadEntry(id: String) {
        _currentEntry.value = _entries.value.find { it.id == id }
    }
    
    fun createNewEntry() {
        val prefs = _userPreferences.value
        _currentEntry.value = JournalEntry(
            title = "Untitled",
            backgroundColor = prefs.defaultBackgroundColor,
            textColor = prefs.defaultTextColor,
            fontSize = prefs.defaultFontSize
        )
    }
    
    fun saveEntry(entry: JournalEntry) {
        val updatedEntry = entry.copy(
            modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
        
        val updatedList = if (_entries.value.any { it.id == updatedEntry.id }) {
            _entries.value.map { if (it.id == updatedEntry.id) updatedEntry else it }
        } else {
            _entries.value + updatedEntry
        }
        
        _entries.value = updatedList.sortedByDescending { it.modifiedAt }
        repository.saveEntries(_entries.value)
    }
    
    fun deleteEntry(id: String) {
        _entries.value = _entries.value.filter { it.id != id }
        repository.saveEntries(_entries.value)
    }
    
    fun renameEntry(id: String, newTitle: String) {
        val updatedList = _entries.value.map { 
            if (it.id == id) {
                it.copy(
                    title = newTitle,
                    modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
            } else {
                it
            }
        }
        _entries.value = updatedList.sortedByDescending { it.modifiedAt }
        repository.saveEntries(_entries.value)
    }
    
    fun clearCurrentEntry() {
        _currentEntry.value = null
    }
    
    fun updatePreferences(preferences: UserPreferences) {
        _userPreferences.value = preferences
        repository.savePreferences(preferences)
    }
}
