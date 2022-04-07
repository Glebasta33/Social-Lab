package com.trusov.sociallab.feature_survey.data.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import com.trusov.sociallab.feature_survey.domain.entity.Question
import com.trusov.sociallab.feature_survey.domain.utils.QuestionTimingCalculator
import com.trusov.sociallab.worker.SubWorkerFactory
import java.text.SimpleDateFormat
import java.util.*
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
            val start = inputData.getString(QUESTION_SURVEY_START) ?: ON_NULL_TIME_PLACEHOLDER
            val end = inputData.getString(QUESTION_SURVEY_END) ?: ON_NULL_TIME_PLACEHOLDER
            if (isInScopeOfSurvey(start, end)) {
                val text = inputData.getString(QUESTION_TEXT) ?: "ошибка"
                val id = inputData.getString(QUESTION_ID) ?: "id"
                notificationHelper.showNotification(
                    "$text currentTime ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(System.currentTimeMillis())}",
                    id,
                    notificationId++
                )
            } else {
                notificationHelper.showNotification("Time is out of boundaries of survey", "id", notificationId++)
            }
        } else {
            notificationHelper.showNotification("Time is out of boundaries of day", "id", notificationId++)
        }
        return Result.success()
    }

    private fun isInBoundariesOfDay(): Boolean {
        val midnight = getCurrentMidnightInMillis()
        val start = midnight + HOURS_9
        val end = midnight + HOURS_22
        return System.currentTimeMillis() in start..end
    }

    private fun isInScopeOfSurvey(startAsString: String, endAsString: String): Boolean {
        val midnight = getCurrentMidnightInMillis()
        val calculator = QuestionTimingCalculator(applicationContext)
        val start = midnight + calculator.parseMillisFromString(startAsString)
        val end = midnight + calculator.parseMillisFromString(endAsString)
        Log.d(
            "QuestionsWorkerTag",
            "midnight ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(midnight)} \n" +
                    "start ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(start)} \n" +
                    "end ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(end)} \n" +
                    "notificationId $notificationId"
        )
        return System.currentTimeMillis() in start..end
    }

    private fun getCurrentMidnightInMillis(): Long {
        val currentTime = System.currentTimeMillis()
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy").format(currentTime)
        val currentMidnight = "$currentDate 00:00:00"
        return SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(currentMidnight).time
    }

    companion object {
        private const val QUESTION_ID = "QUESTION_ID"
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_SURVEY_START = "QUESTION_SURVEY_START"
        private const val QUESTION_SURVEY_END = "QUESTION_SURVEY_END"
        private const val ON_NULL_TIME_PLACEHOLDER = "0:0"
        private const val HOURS_9 = 32400000L
        private const val HOURS_22 = 79200000L
        private var notificationId = 0

        fun schedulePeriodicRequest(interval: Long, question: Question): PeriodicWorkRequest {
            val data = Data.Builder()
            data.putString(QUESTION_ID, question.id)
            data.putString(QUESTION_TEXT, question.text)
            data.putString(QUESTION_SURVEY_START, question.timeScope?.first)
            data.putString(QUESTION_SURVEY_END, question.timeScope?.second)

            return PeriodicWorkRequest.Builder(
                QuestionsWorker::class.java,
                interval,
                TimeUnit.MINUTES
            ).setInputData(data.build())
                .build()
        }

        fun makeOneTimeRequest(question: Question): OneTimeWorkRequest {
            val data = Data.Builder()
            data.putString(QUESTION_ID, question.id)
            data.putString(QUESTION_TEXT, question.text)
            data.putString(QUESTION_SURVEY_START, question.timeScope?.first)
            data.putString(QUESTION_SURVEY_END, question.timeScope?.second)
            return OneTimeWorkRequestBuilder<QuestionsWorker>()
                .setInputData(data.build())
                .build()
        }

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