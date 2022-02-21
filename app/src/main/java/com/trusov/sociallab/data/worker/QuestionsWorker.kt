package com.trusov.sociallab.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.data.RepositoryImpl
import com.trusov.sociallab.domain.entity.Question
import com.trusov.sociallab.presentation.util.NotificationHelper
import kotlinx.coroutines.delay
import javax.inject.Inject

class QuestionsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        var index = 0
        while (true) {
            var question: Question? = null
            firebase.collection("questions").addSnapshotListener { value, error ->
                if (value != null && value.documents.size > index) {
                    val data = value.documents[index++]
                    Log.d("QuestionsWorker", "value: ${data.toString()}")
                    data?.let {
                        question = Question(
                            text = data["text"].toString(),
                            researchId = data["researchId"].toString(),
                            id = data.id
                        )
                    }
                }
                if (error != null) {
                    Log.d("QuestionsWorker", "error: ${error.message}")
                }
            }
            delay(10_000)
            question?.let {
                notificationHelper.showNotification(it.text, it.id)
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

    class Factory @Inject constructor(
        private val firebase: FirebaseFirestore,
        private val notificationHelper: NotificationHelper
    ) : SubWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return QuestionsWorker(
                context,
                workerParameters,
                firebase,
                notificationHelper
            )
        }

    }
}