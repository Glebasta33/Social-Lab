package com.trusov.sociallab.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.data.RepositoryImpl
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.presentation.util.NotificationHelper
import kotlinx.coroutines.delay

class QuestionsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        while (true) {
            var question: Question? = null
            firebase.collection("questions").addSnapshotListener { value, error ->
                if (value != null) {
                    val data = value.documents.find {it["researchId"] == "test"}
                    Log.d("QuestionsWorker", "value: ${data.toString()}")
                    data?.let {
                        question = Question(
                            text = data["text"].toString(),
                            id = data["researchId"].toString()
                        )
                    }
                }
                if (error != null) {
                    Log.d("QuestionsWorker", "error: ${error.message}")
                }
            }
            delay(10_000)
            question?.let {
                notificationHelper.showNotification(it.text)
            }

        }
    }


    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<QuestionsWorker>()
                .build()
        }
    }
}