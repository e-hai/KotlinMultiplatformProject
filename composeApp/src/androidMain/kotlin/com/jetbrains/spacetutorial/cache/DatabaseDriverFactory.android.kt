package com.jetbrains.spacetutorial.cache

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

object AndroidDatabaseDriverFactory : DatabaseDriverFactory {
    private lateinit var context: Application

    fun init(context: Application) {
        AndroidDatabaseDriverFactory.context = context
    }

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "launch.db")
    }
}

actual fun getDatabaseDriverFactory(): DatabaseDriverFactory = AndroidDatabaseDriverFactory
