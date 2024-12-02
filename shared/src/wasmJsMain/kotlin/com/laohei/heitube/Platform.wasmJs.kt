package com.laohei.heitube

class WasmPlatform(override val type: PlatformType=PlatformType.Web) : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()