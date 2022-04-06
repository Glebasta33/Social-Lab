package com.trusov.sociallab.feature_survey.domain.utils

import android.content.Context
import android.util.Log
import com.trusov.sociallab.feature_survey.domain.entity.Question
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class QuestionTimingCalculator (
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
        val startTime = startOfToday + parseMillisFromString(startTimeAsString)
        val endTime = startOfToday + parseMillisFromString(endTimeAsString)
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
        val targetTime = timestapms.filter{ it >= currentTime }.find { abs(it - currentTime) <= period }
//        Log.d("PeriodicCounter", "targetTime $targetTime -> ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(targetTime)}")
        return targetTime ?: 0L
    }

    fun calculateInterval(question: Question): Long {
        val interval = TimeUnit.MINUTES.toMillis(question.periodInMinutes!!.toLong())
        Log.d("PeriodicCounter", "interval: $interval")
        return interval
    }

    fun calculateInitialDelay(question: Question): Long {
        val initialDelay = calculateTime(question) - currentTime
        Log.d("PeriodicCounter", "initialDelay: $initialDelay")
        return initialDelay
    }

    fun parseMillisFromString(timeAsString: String?): Long {
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