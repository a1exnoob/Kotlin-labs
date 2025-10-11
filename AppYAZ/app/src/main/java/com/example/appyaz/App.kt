package com.example.appyaz

import android.app.Application
import com.example.appyaz.db.AppDatabase
import com.example.appyaz.db.UazRepository

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var repository: UazRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val database = AppDatabase.getInstance(this)
        repository = UazRepository(database.uazDao())
    }
}