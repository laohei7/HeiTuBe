package com.laohei.heitube.core.extension

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.round

fun Int.formatTimeString():String{
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
    val formattedSeconds = if (seconds < 10) "0$seconds" else "$seconds"

    return if (hours > 0) {
        val formattedHours = if (hours < 10) "0$hours" else "$hours"
        "$formattedHours:$formattedMinutes:$formattedSeconds"
    } else {
        "$formattedMinutes:$formattedSeconds"
    }
}

fun Int.toViewString(): String {
    return if (this < 10_000) {
        "${this}次观看"
    } else if (this < 1_000_000_000) {
        val number = if ((this * 1.0 % 10_000).toInt() == 0) {
            this / 10_000
        } else {
            round((this * 1.0 / 10_000) * 100) / 100.0
        }
        "${number}万次观看"
    } else {
        val number = if ((this * 1.0 % 1_000_000_000).toInt() == 0) {
            this / 1_000_000_000
        } else {
            round((this * 1.0 / 1_000_000_000) * 100) / 100.0
        }
        "${number}万次观看"
    }
}

fun Long.toTimeAgoString(): String {
    val timestamp = Instant.fromEpochSeconds(this)
    val now = Clock.System.now()
    val duration = now - timestamp
    return when {
        duration.inWholeMinutes < 1 -> "刚刚"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes} 分钟前"
        duration.inWholeHours < 24 -> "${duration.inWholeHours} 小时前"
        duration.inWholeDays < 7 -> "${duration.inWholeDays} 天前"
        duration.inWholeDays < 30 -> "${duration.inWholeDays / 7} 周前"
        duration.inWholeDays < 365 -> "${duration.inWholeDays / 30} 个月前"
        else -> "${duration.inWholeDays / 365} 年前"
    }
}
