package com.trusov.sociallab.domain.entity

import java.text.SimpleDateFormat
import java.util.*

data class TotalScreenTime(
    val millisInDay: Long,
    val millisInLastHour: Long,
    val createdTime: String = getCurrentTime(),
    val createdDate: String = getCurrentDate(),
    val respondentId: String,
    val index: Long = 0
) {
    companion object {
        fun getCurrentTime(): String {
            val cal = Calendar.getInstance()
            val format = SimpleDateFormat("HH:mm:ss")
            return format.format(cal.time)
        }

        fun getCurrentDate(): String {
            val cal = Calendar.getInstance()
            val format = SimpleDateFormat("dd.MM.yy")
            return format.format(cal.time)
        }
    }
}
