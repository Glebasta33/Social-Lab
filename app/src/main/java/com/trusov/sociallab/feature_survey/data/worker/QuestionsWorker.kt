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

    private val calendar = Calendar.getInstance()
    private val hourFormat = SimpleDateFormat("HH")
    private val minFormat = SimpleDateFormat("mm")
    private val secFormat = SimpleDateFormat("ss")
    private val currentTime = calendar.timeInMillis
    private val currentHours = hourFormat.format(currentTime).toString().toInt()
    private val currentMinutes = minFormat.format(currentTime).toString().toInt()
    private val currentSeconds = secFormat.format(currentTime).toString().toInt()
    private var midnight = 0L

    override suspend fun doWork(): Result {
        Log.d("QuestionsWorkerTag", "doWork. currentTime ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(currentTime)}")
            if (isInBoundariesOfDay()) {
            val start = inputData.getString(QUESTION_SURVEY_START) ?: ON_NULL_TIME_PLACEHOLDER
            val end = inputData.getString(QUESTION_SURVEY_END) ?: ON_NULL_TIME_PLACEHOLDER
            if (isInScopeOfSurvey(start, end)) {
                val text = inputData.getString(QUESTION_TEXT) ?: "ошибка"
                val id = inputData.getString(QUESTION_ID) ?: "id"
                notificationHelper.showNotification(
                    "$text currentTime ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(currentTime)}",
                    id
                )
            } else {
                notificationHelper.showNotification("Time is out of boundaries of survey", "id")
            }
        } else {
            notificationHelper.showNotification("Time is out of boundaries of day", "id")
        }
        return Result.success()
    }

    private fun isInBoundariesOfDay(): Boolean {
        setCalendarToCurrentMidnight()
        midnight = calendar.timeInMillis
        val start = midnight + HOURS_9
        val end = midnight + HOURS_22
        return System.currentTimeMillis() in start..end
    }

    private fun isInScopeOfSurvey(startAsString: String, endAsString: String): Boolean {
        val calculator = QuestionTimingCalculator(applicationContext)
        val start = midnight + calculator.parseMillisFromString(startAsString)
        val end = midnight + calculator.parseMillisFromString(endAsString)
        Log.d("QuestionsWorkerTag", " Start: ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(start)}")
        Log.d("QuestionsWorkerTag", " end: ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(end)}")
        return System.currentTimeMillis() in start..end
    }

    private fun setCalendarToCurrentMidnight() {
        calendar.add(Calendar.HOUR_OF_DAY, -currentHours)
        calendar.add(Calendar.MINUTE, -currentMinutes)
        calendar.add(Calendar.SECOND, -currentSeconds)
        midnight = calendar.timeInMillis
    }

    companion object {
        private const val QUESTION_ID = "QUESTION_ID"
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_SURVEY_START = "QUESTION_SURVEY_START"
        private const val QUESTION_SURVEY_END = "QUESTION_SURVEY_END"
        private const val ON_NULL_TIME_PLACEHOLDER = "0:0"
        private const val HOURS_9 = 32400000L
        private const val HOURS_22 = 79200000L

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