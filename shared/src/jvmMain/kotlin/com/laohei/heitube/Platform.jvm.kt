package com.laohei.heitube

class JVMPlatform(override val type: PlatformType = PlatformType.Desktop) : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()