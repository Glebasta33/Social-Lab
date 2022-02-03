package com.trusov.sociallab.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trusov.sociallab.domain.entity.Respondent

@Dao
interface EnterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registerNewRespondent(respondent: Respondent)

    @Query("SELECT * FROM respondent WHERE login == :login AND password == :password LIMIT 1")
    fun getRespondent(login: String, password: String): Respondent
}