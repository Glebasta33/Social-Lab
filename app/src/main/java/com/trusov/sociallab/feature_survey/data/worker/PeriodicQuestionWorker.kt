package com.trusov.sociallab.feature_survey.data.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkerParameters
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import com.trusov.sociallab.feature_survey.domain.entity.Question
import java.util.concurrent.TimeUnit

class PeriodicQuestionWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val text = inputData.getString(QUESTION_TEXT) ?: "text == null"
        val id = inputData.getString(QUESTION_ID) ?: "id == null"
        Log.d("PeriodicQuestionWorker", "text id")
        NotificationHelper(applicationContext as Application).showNotification("PeriodicQuestionWorker: $text", id)
        return Result.success()
    }

    companion object {

        private const val QUESTION_ID = "QUESTION_ID"
        private const val QUESTION_TEXT = "QUESTION_TEXT"

        fun schedulePeriodicRequest(interval: Long, initialDelay: Long, question: Question): PeriodicWorkRequest {

            val data = Data.Builder()
                data.putString(QUESTION_ID, question.id)
                data.putString(QUESTION_TEXT, question.text)

            Log.d("PeriodicQuestionWorker", "question ${question.toString()}")

            return PeriodicWorkRequest.Builder(
                PeriodicQuestionWorker::class.java,
                interval,
                TimeUnit.MILLISECONDS
            )
                .setInputData(data.build())
                .build()
        }
    }
}