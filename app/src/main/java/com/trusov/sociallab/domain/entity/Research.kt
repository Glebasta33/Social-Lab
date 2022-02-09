package com.trusov.sociallab.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Research(
    val topic: String,
    val description: String,
    val questions: List<String> = mutableListOf(),
    val respondents: List<String> = mutableListOf(),
    val id: String = UNDEFINED_ID
) : Parcelable {
    companion object {
        private const val UNDEFINED_ID = ""
    }
}
