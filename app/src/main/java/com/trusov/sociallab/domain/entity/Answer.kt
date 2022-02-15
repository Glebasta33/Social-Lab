package com.trusov.sociallab.domain.entity

import java.text.SimpleDateFormat
import java.util.*

data class Answer(
    val questionId: String,
    val respondentId: String,
    val numberOfAnswer: Int,
    val created: String = getCurrentTime()
) {
    companion object {
        fun getCurrentTime(): String {
            val cal = Calendar.getInstance()
            val format = SimpleDateFormat("hh:mm:ss - dd.MM.yy")
            return format.format(cal.time)
        }
    }
}
