package com.trusov.sociallab.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    private val enterDao: EnterDao,
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : Repository {

    override fun signUp(login: String, password: String) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d("LogcatDebug", "task.isSuccessful $this: $respondent")
                } else {
                    Log.d("LogcatDebug", "!task.isSuccessful $this: ${task.exception}")
                }
            }
    }

    override fun logIn(login: String, password: String) {
        auth.signInWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d("LogcatDebug", "$this: $respondent")
                } else {
                    Log.d("LogcatDebug", "$this: ${task.exception}")
                }
            }
    }

    override suspend fun getCurrentRespondent(): Respondent? {
        auth.currentUser?.let {
            val email = it.email ?: ""

            return Respondent("from", email, id = 3L)
        }
        Log.d("LogcatDebug", "$this auth.currentUser: ${auth.currentUser}")
        return null
    }

    override fun signOut() {
        auth.signOut()
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