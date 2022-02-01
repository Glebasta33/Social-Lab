package com.trusov.sociallab.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.sociallab.domain.model.Question
import com.trusov.sociallab.domain.model.Research
import com.trusov.sociallab.domain.model.Statistics

interface Repository {
    // enter
    fun singUp(login: String, password: String)
    fun logIn(login: String, password: String)

    // researches info
    fun getListOfResearches(): LiveData<List<Research>>
    fun getListOfResearchById(respondentId: Long): LiveData<List<Research>>
    fun getResearchById(researchId: Long): LiveData<Research>
    fun registerToResearch(respondentId: Long, researchId: Long)

    // survey
    fun getQuestion(): Question
    fun answerQuestion(question: Question)

    // respondent info
    fun getListOfAnsweredQuestions(respondentId: Long): LiveData<List<Question>>
    fun getUserStatistics(respondentId: Long): Statistics
}