package com.cardfy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform