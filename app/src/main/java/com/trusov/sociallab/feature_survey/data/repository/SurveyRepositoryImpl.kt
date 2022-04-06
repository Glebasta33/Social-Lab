package com.trusov.sociallab.feature_survey.data.repository

import android.app.Application
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.feature_survey.data.worker.QuestionsWorker
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.feature_survey.domain.entity.Answer
import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended
import com.trusov.sociallab.feature_survey.domain.entity.Question
import com.trusov.sociallab.feature_survey.domain.entity.QuestionType
import com.trusov.sociallab.feature_survey.domain.repository.SurveyRepository
import com.trusov.sociallab.feature_survey.domain.utils.QuestionTimingCalculator
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.ArrayList

@ApplicationScope
class SurveyRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val application: Application
) : SurveyRepository {

    override suspend fun getQuestion() {
        val questions = ArrayList<Question>()
        val workerManager = WorkManager.getInstance(application)
        val calculator = QuestionTimingCalculator(application)
        val collection = firebase.collection("questions").get().await()
        for (data in collection) {
            data?.let {
                var timeScopeResult: Pair<String, String>? = null
                if (data["timeScope"] != null && data["timeScope"] is ArrayList<*>) {
                    val array = data["timeScope"] as ArrayList<*>
                    val arrayOfStrings = array.map { it.toString() }
                    timeScopeResult = arrayOfStrings[0] to arrayOfStrings[1]
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

        for (question in questions) {
            when (question.type) {
                QuestionType.PERIODIC_DAILY -> {
                    Log.d("QuestionsWorker", question.toString())
                }
                QuestionType.PERIODIC_BY_MINUTES -> {
                    workerManager.enqueueUniquePeriodicWork(
                        question.id,
                        ExistingPeriodicWorkPolicy.REPLACE,
                        QuestionsWorker.schedulePeriodicRequest(
                            calculator.calculateInterval(question),
                            question
                        )
                    )
//                    workerManager.enqueueUniqueWork(
//                        question.id,
//                        ExistingWorkPolicy.REPLACE,
//                        QuestionsWorker.makeOneTimeRequest(question)
//                    )
                }
                QuestionType.ONE_TIME -> {
                    Log.d("QuestionsWorker", question.toString())
                }
                QuestionType.CONDITIONAL -> {
                    Log.d("QuestionsWorker", question.toString())
                }
            }
        }
    }

    private fun castStringToQuestionType(type: String): QuestionType {
        return when (type) {
            "PERIODIC_DAILY" -> QuestionType.PERIODIC_DAILY
            "PERIODIC_BY_MINUTES" -> QuestionType.PERIODIC_BY_MINUTES
            "ONE_TIME" -> QuestionType.ONE_TIME
            "CONDITIONAL" -> QuestionType.CONDITIONAL
            else -> throw RuntimeException("Cast to QuestionType exception")
        }
    }

    override fun answerTheQuestion(questionId: String, numberOfAnswer: Int) {
        val answer = Answer(
            questionId = questionId,
            respondentId = auth.currentUser?.uid ?: "error",
            numberOfAnswer = numberOfAnswer
        )
        firebase.collection("answers").add(answer)
    }

    override suspend fun getListOfAnsweredQuestions(): List<AnswerExtended> {

        val listOfAnswersExtended = ArrayList<AnswerExtended>()

        suspend fun getTextOfQuestion(questionId: String): String {
            val questions = firebase.collection("questions").get().await()
            val questionData = questions.documents.find { it.id == questionId }
            return questionData?.get("text").toString()
        }

        suspend fun getResearchTitle(questionId: String): String {
            val questions = firebase.collection("questions").get().await()
            val questionData = questions.documents.find { it.id == questionId }
            val researchId = questionData?.get("researchId").toString()

            val researches = firebase.collection("researches").get().await()
            val researchData = researches.documents.find { it.id == researchId }
            return researchData?.get("topic").toString()
        }

        val answers = firebase.collection("answers")
            .whereEqualTo("respondentId", auth.currentUser?.uid)
            .get()
            .await()
        if (answers != null) {
            for (data in answers.documents) {
                val answerExtended = AnswerExtended(
                    questionId = data["questionId"].toString(),
                    respondentId = data["respondentId"].toString(),
                    numberOfAnswer = data["numberOfAnswer"].toString().toInt(),
                    researchTitle = getResearchTitle(data["questionId"].toString()),
                    textOfQuestion = getTextOfQuestion(data["questionId"].toString()),
                    createdTime = data["createdTime"].toString(),
                    createdDate = data["createdDate"].toString()
                )
                listOfAnswersExtended.add(answerExtended)
            }
        }

        return listOfAnswersExtended
    }
}