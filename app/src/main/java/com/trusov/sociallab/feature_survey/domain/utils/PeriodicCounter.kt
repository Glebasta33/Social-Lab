package com.trusov.sociallab.feature_survey.domain.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trusov.sociallab.feature_survey.data.receiver.NotificationLaunchReceiver
import com.trusov.sociallab.feature_survey.domain.entity.Question
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class PeriodicCounter (
    private val context: Context
) {
    private val hourFormat = SimpleDateFormat("HH")
    private val minFormat = SimpleDateFormat("mm")
    private val secFormat = SimpleDateFormat("ss")
    private val calendar = Calendar.getInstance()
    private val currentTime = calendar.timeInMillis
    private val currentHours = hourFormat.format(currentTime).toString().toInt()
    private val currentMinutes = minFormat.format(currentTime).toString().toInt()
    private val currentSeconds = secFormat.format(currentTime).toString().toInt()
    private var startOfToday = 0L

    init {
        calendar.add(Calendar.HOUR_OF_DAY, -currentHours)
        calendar.add(Calendar.MINUTE, -currentMinutes)
        calendar.add(Calendar.SECOND, -currentSeconds)
        startOfToday = calendar.timeInMillis
    }

    private fun calculateTime(question: Question): Long {
        val startTimeAsString = question.timeScope?.first
        val endTimeAsString = question.timeScope?.second
        val startTime = startOfToday + parseMillis(startTimeAsString)
        val endTime = startOfToday + parseMillis(endTimeAsString)
        val period = TimeUnit.MINUTES.toMillis(question.periodInMinutes!!.toLong())
        val timestapms = mutableListOf<Long>()
        if (currentTime in startTime..endTime) {
            var timestamp = startTime
            while (timestamp in startTime..endTime) {
                timestapms.add(timestamp)
                timestamp += period
                Log.d("PeriodicCounter", "timestamp $timestamp -> ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(timestamp)}")
            }
        } else {
            Log.d("PeriodicCounter", "Current time is out of scope of survey")
        }
        val targetTime = timestapms.find { abs(it - currentTime) <= period }
        Log.d("PeriodicCounter", "targetTime $targetTime -> ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(targetTime)}")
        return targetTime ?: 0L
    }

    // TODO: Реализовать периодическую отправку оповещений (см. QuestionsWorker, NotificationLaunchReceiver, NotificationLaunchService)
    fun runAlarm(question: Question) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationLaunchReceiver::class.java).apply {
            putExtra(QUESTION_TEXT, question.text)
            putExtra(QUESTION_ID, question.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val targetTime = calculateTime(question)
        am.set(AlarmManager.RTC_WAKEUP, targetTime, pendingIntent)
    }


    private fun parseMillis(timeAsString: String?): Long {
        val array = timeAsString?.split(":")
        val numbers = mutableListOf<Int>()
        array?.let {
            for (t in it) {
                numbers.add(t.toInt())
            }
        }
        val result =
            TimeUnit.HOURS.toMillis(numbers[0].toLong()) + TimeUnit.MINUTES.toMillis(numbers[1].toLong())
        return result
    }

    companion object {
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_ID = "QUESTION_ID"
    }
}