package com.kmp.demo

import android.app.Application
import com.jetbrains.spacetutorial.cache.AndroidDatabaseDriverFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidDatabaseDriverFactory.init(this)
    }
}