package com.trusov.sociallab.data

import androidx.lifecycle.LiveData
import com.trusov.sociallab.data.database.EnterDao
import com.trusov.sociallab.di.ApplicationScope
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.entity.Statistics
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

@ApplicationScope
class RepositoryImpl @Inject constructor(
    private val enterDao: EnterDao
) : Repository {

    override fun signUp(login: String, password: String) {
        val respondent = Respondent(password, login, id = System.currentTimeMillis())
        enterDao.registerNewRespondent(respondent)
    }

    override fun logIn(login: String, password: String): Respondent {
        return enterDao.getRespondent(login, password)
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