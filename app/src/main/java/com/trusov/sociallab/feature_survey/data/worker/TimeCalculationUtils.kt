package com.trusov.sociallab.feature_survey.data.worker

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

private const val HOURS_9 = 32400000L
private const val HOURS_22 = 79200000L

fun parseMillisFromString(timeAsString: String?): Long {
    val array = timeAsString?.split(":")
    val numbers = mutableListOf<Int>()
    array?.let {
        for (t in it) {
            numbers.add(t.toInt())
        }
    }
    val hours = TimeUnit.HOURS.toMillis(numbers[0].toLong())
    val minutes = TimeUnit.MINUTES.toMillis(numbers[1].toLong())
    return hours + minutes
}

private fun getCurrentMidnightInMillis(): Long {
    val currentTime = System.currentTimeMillis()
    val currentDate: String = SimpleDateFormat("dd-MM-yyyy").format(currentTime)
    val currentMidnight = "$currentDate 00:00:00"
    return SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(currentMidnight).time
}

fun isInBoundariesOfDay(): Boolean {
    val midnight = getCurrentMidnightInMillis()
    val start = midnight + HOURS_9
    val end = midnight + HOURS_22
    return System.currentTimeMillis() in start..end
}

fun isInScopeOfSurvey(startAsString: String, endAsString: String): Boolean {
    val midnight = getCurrentMidnightInMillis()
    val start = midnight + parseMillisFromString(startAsString)
    val end = midnight + parseMillisFromString(endAsString)
    return System.currentTimeMillis() in start..end
}