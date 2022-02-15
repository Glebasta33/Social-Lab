package com.trusov.sociallab.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trusov.sociallab.utils.Constants.Gender
import com.trusov.sociallab.utils.Constants.UNKNOWN_DATE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Respondent(
    val password: String,
    val login: String,
    val genderIsMale: Gender = Gender.NOT_SPECIFIED,
    val birthDate: String = UNKNOWN_DATE,
    @PrimaryKey(autoGenerate = false)
    val id: Long
) : Parcelable