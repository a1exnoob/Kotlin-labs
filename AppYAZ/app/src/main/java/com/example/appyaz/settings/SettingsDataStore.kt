package com.example.appyaz.settings

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

val Context.settingsDataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {
    private val KEY_LANG = stringPreferencesKey("lang")
    private val KEY_DARK = booleanPreferencesKey("dark")

    val langFlow: Flow<String> =
        context.settingsDataStore.data.map { it[KEY_LANG] ?: "en" }

    val darkFlow: Flow<Boolean> =
        context.settingsDataStore.data.map { it[KEY_DARK] ?: false }

    suspend fun setLang(lang: String) {
        context.settingsDataStore.edit { it[KEY_LANG] = lang }
    }

    suspend fun setDark(enabled: Boolean) {
        context.settingsDataStore.edit { it[KEY_DARK] = enabled }
    }

    suspend fun getLang(): String {
        return langFlow.first()
    }

    suspend fun getDark(): Boolean {
        return darkFlow.first()
    }
}