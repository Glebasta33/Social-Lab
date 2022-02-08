package com.trusov.sociallab.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.entity.Statistics

interface Repository {
    // enter
    fun signUp(login: String, password: String)
    fun logIn(login: String, password: String)
    suspend fun getCurrentRespondent(): Respondent?
    fun signOut()

    // researches info
    fun getListOfResearches(): LiveData<List<Research>>
    fun getListOfResearchById(respondentId: String): LiveData<List<Research>>
    fun getResearchById(researchId: String): LiveData<Research>
    fun registerToResearch(respondentId: String, researchId: String)

    // survey
    fun getQuestion(): Question
    fun answerQuestion(question: Question)

    // respondent info
    fun getListOfAnsweredQuestions(respondentId: String): LiveData<List<Question>>
    fun getUserStatistics(respondentId: String): Statistics
}