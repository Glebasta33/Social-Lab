package com.trusov.sociallab.data

import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.USAGE_STATS_SERVICE
import android.graphics.drawable.Drawable
import com.trusov.sociallab.domain.entity.ScreenTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UStats @Inject constructor(
    private val application: Application,
    private val usm: UsageStatsManager
) {
    private val hourFormat = SimpleDateFormat("HH")
    private val minFormat = SimpleDateFormat("mm")
    private val secFormat = SimpleDateFormat("ss")

    fun getUsageStatsList(): List<UsageStats> {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        val currentHours = hourFormat.format(currentTime).toString().toInt()
        val currentMinutes = minFormat.format(currentTime).toString().toInt()
        val currentSeconds = secFormat.format(currentTime).toString().toInt()
        calendar.add(Calendar.HOUR_OF_DAY, -currentHours)
        calendar.add(Calendar.MINUTE, -currentMinutes)
        calendar.add(Calendar.SECOND, -currentSeconds)
        val startTime = calendar.timeInMillis
        return usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, currentTime)
    }

    fun getListOfScreenTime(): List<ScreenTime> {
        val usageStatsList = getUsageStatsList()
        val screenTimes = mutableListOf<ScreenTime>()
        for (u in usageStatsList) {
            if (u.totalTimeInForeground != 0L) {
                val s = u.totalTimeInForeground / 1000
                val hours = s / 3600
                val minutes = (s % 3600) / 60
                val seconds = (s % 60)
                val screenTime =
                    ScreenTime(
                        hours,
                        minutes,
                        seconds,
                        getAppLabel(u.packageName),
                        getAppIcon(u.packageName)
                    )
                screenTimes.add(screenTime)
            }
        }
        return screenTimes
    }

    fun getTotalScreenTime(): ScreenTime {
        var totalScreenTime = 0L
        val usageStatsList = getUsageStatsList()
        for (u in usageStatsList) {
            if (u.totalTimeInForeground != 0L) {
                totalScreenTime += u.totalTimeInForeground
            }
        }
        val s = totalScreenTime / 1000
        val hours = s / 3600
        val minutes = (s % 3600) / 60
        val seconds = (s % 60)
        return ScreenTime(hours, minutes, seconds, "Сегодня")
    }

    private fun getAppLabel(pkg: String): String {
        val pm = application.packageManager
        val info = pm.getApplicationInfo(pkg, 0)
        val label = pm.getApplicationLabel(info)
        return label.toString()
    }

    private fun getAppIcon(pkg: String): Drawable {
        val pm = application.packageManager
        val info = pm.getApplicationInfo(pkg, 0)
        return pm.getApplicationIcon(info)
    }
}