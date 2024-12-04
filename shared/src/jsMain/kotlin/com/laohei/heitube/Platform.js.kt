package com.laohei.heitube

class JsPlatform(override val type: PlatformType=PlatformType.Web) : Platform {
    override val name: String = "Web with Kotlin/Js"
}

actual fun getPlatform(): Platform = JsPlatform()