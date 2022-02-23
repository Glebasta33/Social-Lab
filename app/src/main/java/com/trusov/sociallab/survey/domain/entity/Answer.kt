package com.trusov.sociallab.survey.domain.entity

import java.text.SimpleDateFormat
import java.util.*

data class Answer(
    val questionId: String,
    val respondentId: String,
    val numberOfAnswer: Int,
    val createdTime: String = getCurrentTime(),
    val createdDate: String = getCurrentDate()
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
