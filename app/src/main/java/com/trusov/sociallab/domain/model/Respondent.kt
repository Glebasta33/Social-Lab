package com.trusov.sociallab.domain.model

import androidx.room.Entity
import com.trusov.sociallab.common.Constants.Gender
import com.trusov.sociallab.common.Constants.UNKNOWN_ID
import java.util.*

@Entity
data class Respondent(
    private val password: String,
    private val login: String,
    private val genderIsMale: Gender = Gender.NOT_SPECIFIED,
    private val birthDate: Date = Date(),
    private val id: Long = UNKNOWN_ID
)