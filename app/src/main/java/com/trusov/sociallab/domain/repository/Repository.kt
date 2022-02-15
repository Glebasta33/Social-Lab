package com.trusov.sociallab.domain.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.domain.entity.AnswerExtended
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.entity.Statistics

interface Repository {
    // enter
    fun signUp(login: String, password: String)
    fun logIn(login: String, password: String)
    suspend fun getCurrentUser(): FirebaseUser?
    fun signOut()

    // researches info
    fun getListOfResearches(): LiveData<List<Research>>
    fun getListOfResearchById(respondentId: String): LiveData<List<Research>>
    fun getResearchById(researchId: String): LiveData<Research>
    fun registerToResearch(researchId: String)
    fun unregisterFromResearch(researchId: String)

    // survey
    fun getQuestion()
    fun answerTheQuestion(questionId: String, numberOfAnswer: Int)

    // respondent info
    suspend fun getListOfAnsweredQuestions(): List<AnswerExtended>
    fun getUserStatistics(respondentId: String): Statistics
}