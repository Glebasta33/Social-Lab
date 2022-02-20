package com.trusov.sociallab.domain.entity

data class ScreenTime(
    val hours: Long,
    val minutes: Long,
    val seconds: Long,
    var appName: String
) {
    fun formattedTime(): String {
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
