package com.trusov.sociallab.feature_statistics.domain.entity

import android.graphics.drawable.Drawable

data class AppScreenTime(
    val hours: Long,
    val minutes: Long,
    val seconds: Long,
    var appName: String,
    var icon: Drawable? = null
) {
    fun formattedTime(): String {
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
