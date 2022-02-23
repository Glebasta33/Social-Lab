package com.trusov.sociallab.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.data.worker.QuestionsWorker
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.domain.entity.*
import com.trusov.sociallab.domain.repository.Repository
import com.trusov.sociallab.survey.domain.entity.Answer
import com.trusov.sociallab.survey.domain.entity.AnswerExtended
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.ArrayList

@ApplicationScope
class RepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val application: Application,
    private val usageStats: UStats
) : Repository {

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

    override fun getListOfScreenTime(): List<AppScreenTime> {
        return usageStats.getListOfScreenTime()
    }

    override fun getTotalScreenTime(): AppScreenTime {
        return usageStats.getTotalScreenTime()
    }

    override fun checkUsageStatsPermission(): Boolean {
        return usageStats.getUsageStatsList().isEmpty()
    }

    companion object {
        private const val TAG = "RepositoryImplTag"
    }
}