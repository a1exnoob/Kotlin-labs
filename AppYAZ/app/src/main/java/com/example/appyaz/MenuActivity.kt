package com.example.appyaz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.appyaz.databinding.ActivityMenuBinding
import com.example.appyaz.settings.SettingsDataStore
import com.example.appyaz.ui.BaseActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuActivity : BaseActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var ds: SettingsDataStore
    private var switchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateTexts()

        ds = SettingsDataStore(this)

        lifecycleScope.launch {
            ds.darkFlow.collect { isDark ->
                binding.switchDark.isChecked = isDark
                applyTheme(isDark)
            }
        }

        binding.switchDark.setOnCheckedChangeListener { _, checked ->
            switchJob?.cancel()
            switchJob = lifecycleScope.launch {
                delay(300)
                ds.setDark(checked)
            }
        }

        binding.buttonToggleLang.setOnClickListener {
            lifecycleScope.launch {
                val current = ds.getLang()
                val next = if (current == "ru") "en" else "ru"
                updateLocale(next)
            }
        }

        binding.buttonOpenList.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun updateTexts() {
        with(binding) {
            textMenu.text = getString(R.string.menu_title)
            buttonOpenList.text = getString(R.string.menu_open_list)
            buttonToggleLang.text = getString(R.string.menu_toggle_lang)
            switchDark.text = getString(R.string.dark_theme)
        }
    }

    private fun applyTheme(isDark: Boolean) {
        val mode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onDestroy() {
        super.onDestroy()
        switchJob?.cancel()
    }
}