package com.trusov.sociallab.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.USAGE_STATS_SERVICE
import android.util.Log
import com.trusov.sociallab.domain.entity.ScreenTime
import java.text.SimpleDateFormat
import java.util.*

object UStats {
    private val dateFormat = SimpleDateFormat("M-d-yyyy HH:mm:ss")
    private val hourFormat = SimpleDateFormat("HH")
    private val minFormat = SimpleDateFormat("mm")
    private val secFormat = SimpleDateFormat("ss")
    val TAG = UStats.javaClass.simpleName

    fun getListOfScreenTime(context: Context): List<ScreenTime> {
        val usageStatsList = getUsageStatsList(context)
        val screenTimes = mutableListOf<ScreenTime>()
        for (u in usageStatsList) {
            if (u.totalTimeInForeground != 0L) {
                val s = u.totalTimeInForeground / 1000
                val hours = s / 3600
                val minutes = (s % 3600) / 60
                val seconds = (s % 60)
                val screenTime = ScreenTime(hours, minutes, seconds, u.packageName)
                screenTimes.add(screenTime)
            }
        }
        return screenTimes
    }

    fun getTotalScreenTime(context: Context): ScreenTime {
        var totalScreenTime = 0L
        val usageStatsList = getUsageStatsList(context)
        for (u in usageStatsList) {
            if (u.totalTimeInForeground != 0L) {
                totalScreenTime += u.totalTimeInForeground
            }
        }
        val s = totalScreenTime / 1000
        val hours = s / 3600
        val minutes = (s % 3600) / 60
        val seconds = (s % 60)
        Log.d("MainViewModelTag", "getTotalScreenTime: $s")
        return ScreenTime(hours, minutes, seconds, "Сегодня")
    }

    fun getUsageStatsList(context: Context): List<UsageStats> {
        val usm = context.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        val currentHours = hourFormat.format(currentTime).toString().toInt()
        val currentMinutes = minFormat.format(currentTime).toString().toInt()
        val currentSeconds = secFormat.format(currentTime).toString().toInt()
        calendar.add(Calendar.HOUR_OF_DAY, -currentHours)
        calendar.add(Calendar.MINUTE, -currentMinutes)
        calendar.add(Calendar.SECOND, -currentSeconds)
        val startTime = calendar.timeInMillis

        Log.d(TAG, "start: ${dateFormat.format(startTime)}")
        Log.d(TAG, "now: ${dateFormat.format(currentTime)}")

        return usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, currentTime)
    }
}