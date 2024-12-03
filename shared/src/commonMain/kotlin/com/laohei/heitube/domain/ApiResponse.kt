package com.laohei.heitube.domain

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: T
)