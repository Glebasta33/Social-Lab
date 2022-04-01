package com.trusov.sociallab.feature_survey.data.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.trusov.sociallab.worker.SubWorkerFactory
import com.trusov.sociallab.feature_survey.domain.entity.Question
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import com.trusov.sociallab.feature_survey.domain.entity.QuestionType
import com.trusov.sociallab.feature_survey.domain.utils.PeriodicCounter
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuestionsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {

    private val questions = ArrayList<Question>()

    override suspend fun doWork(): Result {
        val collection = firebase.collection("questions").get().await()
        if (collection.size() != questions.size) {
            updateQuestions(collection)
            showQuestion()
            Log.d("QuestionsWorker", "updateQuestions")
        } else {
            showQuestion()
            Log.d("QuestionsWorker", "showQuestion")
        }
        return Result.success()
    }

    private fun showQuestion() {
        for (question in questions) {
            when(question.type) {
                QuestionType.PERIODIC_DAILY -> {
                    Log.d("QuestionsWorker", question.toString())
                }
                QuestionType.PERIODIC_BY_MINUTES -> {
                    PeriodicCounter(applicationContext).runAlarm(question)
                    Log.d("QuestionsWorker", question.toString())
                }
                QuestionType.ONE_TIME -> {
                    Log.d("QuestionsWorker", question.toString())
                }
                QuestionType.CONDITIONAL -> {
                    Log.d("QuestionsWorker", question.toString())
                }
//            question?.let {
//                notificationHelper.showNotification(it.text, it.id)
//            }
            }
        }
    }

    private fun updateQuestions(collection: QuerySnapshot) {
        fun castStringToQuestionType(type: String): QuestionType {
            return when(type) {
                "PERIODIC_DAILY" -> QuestionType.PERIODIC_DAILY
                "PERIODIC_BY_MINUTES" -> QuestionType.PERIODIC_BY_MINUTES
                "ONE_TIME" -> QuestionType.ONE_TIME
                "CONDITIONAL" -> QuestionType.CONDITIONAL
                else -> throw RuntimeException("Cast to QuestionType exception")
            }
        }
        questions.clear()
        for (data in collection) {
            data?.let {
                var timeScopeResult: Pair<String, String>? = null
                if (data["timeScope"] != null && data["timeScope"] is ArrayList<*>) {
                    val array = data["timeScope"] as ArrayList<String>
                    timeScopeResult = array[0] to array[1]
                }
                val question = Question(
                    text = data["text"].toString(),
                    researchId = data["researchId"].toString(),
                    id = data.id,
                    type = castStringToQuestionType(data["type"].toString()),
                    timeScope = timeScopeResult,
                    periodInMinutes = data["periodInMinutes"]?.toString()?.toInt()
                )
                questions.add(question)
            }
        }
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