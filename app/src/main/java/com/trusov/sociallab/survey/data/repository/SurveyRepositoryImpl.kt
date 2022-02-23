package com.trusov.sociallab.survey.data.repository

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.data.worker.QuestionsWorker
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.survey.domain.entity.Answer
import com.trusov.sociallab.survey.domain.entity.AnswerExtended
import com.trusov.sociallab.survey.domain.repository.SurveyRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ApplicationScope
class SurveyRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val application: Application
) : SurveyRepository {
    override fun getQuestion() {
        val workerManager = WorkManager.getInstance(application)
        workerManager.enqueueUniquePeriodicWork(
            QuestionsWorker.NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            QuestionsWorker.makePeriodicRequest()
        )
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