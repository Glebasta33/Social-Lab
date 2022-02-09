package com.trusov.sociallab.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.di.ApplicationScope
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.domain.entity.Research
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.domain.entity.Statistics
import com.trusov.sociallab.domain.repository.Repository
import javax.inject.Inject

@ApplicationScope
class RepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : Repository {

    override fun signUp(login: String, password: String) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d(TAG, "task.isSuccessful $this: $respondent")
                } else {
                    Log.d(TAG, "!task.isSuccessful $this: ${task.exception}")
                }
            }
    }

    override fun logIn(login: String, password: String) {
        auth.signInWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d(TAG, "$this: $respondent")
                } else {
                    Log.d(TAG, "$this: ${task.exception}")
                }
            }
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        auth.currentUser?.let {
            return it
        }
        return null
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getListOfResearches(): LiveData<List<Research>> {
        val listOfResearches = ArrayList<Research>()
        val liveData = MutableLiveData<List<Research>>()
        firebase.collection("researches").addSnapshotListener { value, error ->
            if (value != null) {
                listOfResearches.clear()
                for (data in value.documents) {
                    val research = Research(
                        topic = data["topic"].toString(),
                        description = data["description"].toString(),
                        id = data.id,
                        respondents = data["respondents"] as ArrayList<String>
                    )
                    listOfResearches.add(research)
                }
                liveData.value = listOfResearches
            }
            if (error != null) {
                Log.d(TAG, "error: ${error.message}")
            }
        }
        return liveData
    }

    override fun getListOfResearchById(respondentId: String): LiveData<List<Research>> {
        TODO("Not yet implemented")
    }

    override fun getResearchById(researchId: String): LiveData<Research> {
        val liveData = MutableLiveData<Research>()
        firebase.collection("researches").addSnapshotListener { value, error ->
            if (value != null) {
                val data = value.documents.find { it.id == researchId }
                data?.let {
                    val research = Research(
                        topic = data["topic"].toString(),
                        description = data["description"].toString(),
                        id = data.id,
                        respondents = data["respondents"] as ArrayList<String>
                    )
                    liveData.value = research
                }
            }
            if (error != null) {
                Log.d(TAG, "error: ${error.message}")
            }
        }
        return liveData
    }

    override fun registerToResearch(researchId: String) {
        val userId = auth.currentUser?.uid
        firebase.collection("researches").document(researchId)
            .update("respondents", FieldValue.arrayUnion(userId))
            .addOnFailureListener {
                Log.d(TAG, "e: ${it.message}")
            }
    }

    override fun unregisterFromResearch(researchId: String) {
        val userId = auth.currentUser?.uid
        firebase.collection("researches").document(researchId)
            .update("respondents", FieldValue.arrayRemove(userId))
            .addOnFailureListener {
                Log.d(TAG, "e: ${it.message}")
            }
    }

    override fun getQuestion(): Question {
        TODO("Not yet implemented")
    }

    override fun answerQuestion(question: Question) {
        TODO("Not yet implemented")
    }

    override fun getListOfAnsweredQuestions(respondentId: String): LiveData<List<Question>> {
        TODO("Not yet implemented")
    }

    override fun getUserStatistics(respondentId: String): Statistics {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "RepositoryImpl"
    }
}