package com.trusov.sociallab.feature_survey.data.worker

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.trusov.sociallab.App
import com.trusov.sociallab.feature_survey.data.receiver.NotificationHelper
import javax.inject.Inject

class NotificationLaunchService : IntentService("NotificationLaunchService") {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onHandleIntent(intent: Intent?) {
        (application as App).component.inject(this)
        intent?.let{
            if (it.hasExtra(QUESTION_TEXT) && it.hasExtra(QUESTION_ID)) {
                val text = it.getStringExtra(QUESTION_TEXT)!!
                val id = it.getStringExtra(QUESTION_ID)!!
                Log.d("LaunchService", "$text $id")
                notificationHelper.showNotification(text, id)
            }
        }
    }

    companion object {
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_ID = "QUESTION_ID"
    }
}