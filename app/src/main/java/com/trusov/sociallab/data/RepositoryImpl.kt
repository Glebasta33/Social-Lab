package com.trusov.sociallab.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.trusov.sociallab.data.database.AppDatabase
import com.trusov.sociallab.data.database.EnterDao
import com.trusov.sociallab.di.ApplicationScope
import com.trusov.sociallab.domain.model.Question
import com.trusov.sociallab.domain.model.Research
import com.trusov.sociallab.domain.model.Respondent
import com.trusov.sociallab.domain.model.Statistics
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

@ApplicationScope
class RepositoryImpl @Inject constructor(
    private val enterDao: EnterDao
) : Repository {

    override fun signUp(login: String, password: String) {
        val respondent = Respondent(password, login)
        enterDao.registerNewRespondent(respondent)
    }

    // нужно ли создать другую функцию для получения текущего респондента из базы
    override fun logIn(login: String, password: String): Respondent {
        TODO("Not yet implemented")
    }

    override fun getListOfResearches(): LiveData<List<Research>> {
        TODO("Not yet implemented")
    }

    override fun getListOfResearchById(respondentId: Long): LiveData<List<Research>> {
        TODO("Not yet implemented")
    }

    override fun getResearchById(researchId: Long): LiveData<Research> {
        TODO("Not yet implemented")
    }

    override fun registerToResearch(respondentId: Long, researchId: Long) {
        TODO("Not yet implemented")
    }

    override fun getQuestion(): Question {
        TODO("Not yet implemented")
    }

    override fun answerQuestion(question: Question) {
        TODO("Not yet implemented")
    }

    override fun getListOfAnsweredQuestions(respondentId: Long): LiveData<List<Question>> {
        TODO("Not yet implemented")
    }

    override fun getUserStatistics(respondentId: Long): Statistics {
        TODO("Not yet implemented")
    }
}