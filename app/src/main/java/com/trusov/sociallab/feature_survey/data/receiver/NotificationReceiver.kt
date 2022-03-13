package com.trusov.sociallab.feature_survey.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trusov.sociallab.App
import com.trusov.sociallab.feature_survey.domain.usa_case.AnswerTheQuestionUseCase
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var answerTheQuestionUseCase: AnswerTheQuestionUseCase

    override fun onReceive(context: Context, intent: Intent) {

        (context.applicationContext as App).component.inject(this)

        Log.d("NotificationReceiverTag", "${intent.getIntExtra(NUMBER_OF_ANSWER, UNKNOWN_NUMBER_OF_ANSWER)} ${intent.getStringExtra(
            QUESTION_ID
        )}")

        val questionId = intent.getStringExtra(QUESTION_ID) ?: "error"
        val numberOfAnswer = intent.getIntExtra(NUMBER_OF_ANSWER, UNKNOWN_NUMBER_OF_ANSWER)

        answerTheQuestionUseCase(questionId, numberOfAnswer)
    }

    companion object {
        private const val NUMBER_OF_ANSWER = "NUMBER_OF_ANSWER"
        private const val QUESTION_ID = "QUESTION_ID"
        private const val UNKNOWN_NUMBER_OF_ANSWER = 0
    }
}
