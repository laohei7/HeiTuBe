package com.laohei.heitube

enum class PlatformType {
    Android, Desktop, Web, IOS
}

interface Platform {
    val name: String
    val type:PlatformType
}

expect fun getPlatform(): Platform