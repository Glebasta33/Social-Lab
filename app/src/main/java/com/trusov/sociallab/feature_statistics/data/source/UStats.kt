package com.trusov.sociallab.feature_statistics.data.source

import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.graphics.drawable.Drawable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime
import com.trusov.sociallab.feature_statistics.domain.entity.TotalScreenTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UStats @Inject constructor(
    private val application: Application,
    private val usm: UsageStatsManager,
    private val auth: FirebaseAuth,
    private val firebase: FirebaseFirestore
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

    fun getListOfScreenTime(): List<AppScreenTime> {
        val usageStatsList = getUsageStatsList()
        val screenTimes = mutableListOf<AppScreenTime>()
        for (u in usageStatsList) {
            if (u.totalTimeInForeground != 0L) {
                val s = u.totalTimeInForeground / 1000
                val hours = s / 3600
                val minutes = (s % 3600) / 60
                val seconds = (s % 60)
                val screenTime =
                    AppScreenTime(
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

    fun getTotalScreenTime(): AppScreenTime {
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
        return AppScreenTime(hours, minutes, seconds, "Сегодня")
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

    fun saveCurrentTotalScreenTime() {
        CoroutineScope(Dispatchers.IO).launch {
            var currentTotalScreenTime = 0L
            val usageStatsList = getUsageStatsList()
            for (u in usageStatsList) {
                if (u.totalTimeInForeground != 0L) {
                    currentTotalScreenTime += u.totalTimeInForeground
                }
            }
            val millisInLastHour = currentTotalScreenTime - getScreenTimeHourAgo()
            Log.d("ScreenTimeSaverTag", "UStats.millisInLastHour: $millisInLastHour")
            val totalScreenTime = TotalScreenTime(
                millisInDay = currentTotalScreenTime,
                millisInLastHour = millisInLastHour,
                respondentId = auth.currentUser?.uid ?: "unknown uid",
                index = getPreviousIndex() + 1
            )
            firebase.collection("total_screen_time").add(totalScreenTime)
            Log.d("ScreenTimeSaverTag", "UStats.totalScreenTime: $totalScreenTime")
        }
    }

    private suspend fun getPreviousIndex(): Long {
        val screenTimeCollection = firebase.collection("total_screen_time")
            .whereEqualTo("respondentId", auth.currentUser?.uid)
            .get()
            .await()
        val screenTimeDoc =
            screenTimeCollection.documents.maxByOrNull { it["index"].toString().toLong() }
        val previousIndex = screenTimeDoc?.get("index")?.toString()?.toLong() ?: 0L
        Log.d("ScreenTimeSaverTag", "UStats.previousIndex: $previousIndex")
        return previousIndex
    }

    private suspend fun getScreenTimeHourAgo(): Long {
        val screenTimeCollection = firebase.collection("total_screen_time")
            .whereEqualTo("respondentId", auth.currentUser?.uid)
            .get()
            .await()
        val screenTimeDoc = screenTimeCollection.documents.find {
            it["index"].toString().toLong() == getPreviousIndex()
        }
        return screenTimeDoc?.get("millisInDay")?.toString()?.toLong() ?: 0L
    }
}