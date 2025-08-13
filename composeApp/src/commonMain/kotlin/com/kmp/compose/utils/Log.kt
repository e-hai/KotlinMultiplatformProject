package com.kmp.compose.utils

import co.touchlab.kermit.Logger

object Log {

    fun d(tag: String, message: String) {
        Logger.withTag(tag).d(message)
    }

    fun e(tag: String, message: String) {
        Logger.withTag(tag).e(message)
    }

    fun i(tag: String, message: String) {
        Logger.withTag(tag).i(message)
    }

    fun w(tag: String, message: String) {
        Logger.withTag(tag).w(message)
    }

    fun v(tag: String, message: String) {
        Logger.withTag(tag).v(message)
    }

    fun wtf(tag: String, message: String, throwable: Throwable) {
        Logger.withTag(tag).e(message, throwable)
    }
}