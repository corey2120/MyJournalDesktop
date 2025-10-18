package com.cobrien.myjournal

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.io.File

class JournalRepository {
    private val json = Json { 
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    
    private val dataDir = File(System.getProperty("user.home"), ".myjournal")
    private val entriesFile = File(dataDir, "entries.json")
    private val preferencesFile = File(dataDir, "preferences.json")
    
    init {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
    }
    
    fun loadEntries(): List<JournalEntry> {
        return try {
            if (entriesFile.exists()) {
                val jsonString = entriesFile.readText()
                json.decodeFromString<List<JournalEntry>>(jsonString)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    fun saveEntries(entries: List<JournalEntry>) {
        try {
            val jsonString = json.encodeToString(entries)
            entriesFile.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun loadPreferences(): UserPreferences {
        return try {
            if (preferencesFile.exists()) {
                val jsonString = preferencesFile.readText()
                json.decodeFromString<UserPreferences>(jsonString)
            } else {
                UserPreferences()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UserPreferences()
        }
    }
    
    fun savePreferences(preferences: UserPreferences) {
        try {
            val jsonString = json.encodeToString(preferences)
            preferencesFile.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
