package com.trusov.sociallab.feature_researches.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.feature_researches.domain.entity.Research
import com.trusov.sociallab.feature_researches.domain.repository.ResearchesRepository
import javax.inject.Inject

@ApplicationScope
class ResearchesRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ResearchesRepository {

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

    companion object {
        private const val TAG = "ResearchesRepoImplTag"
    }
}