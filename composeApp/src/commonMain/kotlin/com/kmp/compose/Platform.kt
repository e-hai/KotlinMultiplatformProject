package com.kmp.compose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform