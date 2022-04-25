package com.trusov.sociallab.feature_survey.data.worker

import android.content.Context
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import com.trusov.sociallab.feature_survey.domain.entity.Question
import com.trusov.sociallab.worker.SubWorkerFactory
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuestionsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (isInBoundariesOfDay()) {
            val start = inputData.getString(QUESTION_SURVEY_START)!!
            val end = inputData.getString(QUESTION_SURVEY_END)!!
            if (isInScopeOfSurvey(start, end)) {
                val text = inputData.getString(QUESTION_TEXT)!!
                val id = inputData.getString(QUESTION_ID)!!
                notificationHelper.showNotification(text, id, notificationId++)
            }
        }
        return Result.success()
    }

    companion object {
        private const val QUESTION_ID = "QUESTION_ID"
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_SURVEY_START = "QUESTION_SURVEY_START"
        private const val QUESTION_SURVEY_END = "QUESTION_SURVEY_END"
        private var notificationId = 0

        fun schedulePeriodicRequest(interval: Long, question: Question): PeriodicWorkRequest {
            val data = Data.Builder().apply {
                putString(QUESTION_ID, question.id)
                putString(QUESTION_TEXT, question.text)
                putString(QUESTION_SURVEY_START, question.timeScope?.first)
                putString(QUESTION_SURVEY_END, question.timeScope?.second)
            }.build()
            return PeriodicWorkRequestBuilder<QuestionsWorker>(
                interval,
                TimeUnit.MINUTES
            ).setInputData(data)
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