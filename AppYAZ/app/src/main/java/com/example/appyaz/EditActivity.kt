package com.example.appyaz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelId = intent.getIntExtra("modelId", -1)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, EditFragment().apply {
                arguments = Bundle().apply { putInt("modelId", modelId) }
            })
            .commit()
    }
}