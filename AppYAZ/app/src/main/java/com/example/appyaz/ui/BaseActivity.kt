package com.example.appyaz.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appyaz.settings.SettingsDataStore
import kotlinx.coroutines.launch
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var settingsDataStore: SettingsDataStore
    protected var currentLang: String = "en"

    override fun attachBaseContext(newBase: Context) {
        settingsDataStore = SettingsDataStore(newBase)
        currentLang = kotlinx.coroutines.runBlocking {
            settingsDataStore.getLang()
        }
        super.attachBaseContext(updateLocale(newBase, currentLang))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyLocale()
    }

    private fun updateLocale(context: Context, lang: String): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun applyLocale() {
        val lang = kotlinx.coroutines.runBlocking {
            settingsDataStore.getLang()
        }
        currentLang = lang

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    protected fun updateLocale(lang: String) {
        lifecycleScope.launch {
            settingsDataStore.setLang(lang)
            currentLang = lang
            recreate()
        }
    }

    protected fun getStringResource(name: String): String {
        return try {
            val resId = resources.getIdentifier(name, "string", packageName)
            if (resId != 0) getString(resId) else name
        } catch (e: Exception) {
            name
        }
    }
}