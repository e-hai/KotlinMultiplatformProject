package com.kmp.compose.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object TimeUtil {

    @OptIn(ExperimentalTime::class)
    fun formatTimeNow(): String {
        val now = Clock.System.now()
        val dateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val formattedDateTime = "${dateTime.year}" +
                dateTime.month.number.toString().padStart(2, '0') +
                dateTime.day.toString().padStart(2, '0') +
                "-" +
                dateTime.hour.toString().padStart(2, '0') +
                dateTime.minute.toString().padStart(2, '0') +
                dateTime.second.toString().padStart(2, '0')
        return formattedDateTime
    }
}