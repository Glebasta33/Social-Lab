package com.trusov.sociallab.feature_survey.data.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.worker.SubWorkerFactory
import com.trusov.sociallab.feature_survey.domain.entity.Question
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuestionsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        var index = 0
            var question: Question? = null
            firebase.collection("questions").addSnapshotListener { value, error ->
                if (value != null && value.documents.size > index) {
                    val data = value.documents[index++]
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
        return Result.success()
    }


    companion object {
        const val NAME = "QuestionsWorker"
        fun makePeriodicRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequest.Builder(
                QuestionsWorker::class.java,
                15,
                TimeUnit.MINUTES
            ).build()
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