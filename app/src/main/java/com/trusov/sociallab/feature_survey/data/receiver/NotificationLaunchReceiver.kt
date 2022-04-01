package com.trusov.sociallab.feature_survey.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trusov.sociallab.feature_survey.data.worker.NotificationLaunchService
import com.trusov.sociallab.feature_survey.domain.utils.PeriodicCounter

class NotificationLaunchReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let{
            if (it.hasExtra(QUESTION_TEXT) && it.hasExtra(QUESTION_ID)) {
                val text = it.getStringExtra(QUESTION_TEXT)!!
                val id = it.getStringExtra(QUESTION_ID)!!

                Log.d("LaunchReceiver", "$text $id")

                val newIntent = Intent(context, NotificationLaunchService::class.java).apply {
                    putExtra(QUESTION_TEXT, text)
                    putExtra(QUESTION_ID, id)
                }
                context?.startService(newIntent)
            }
        }
    }

    companion object {
        private const val QUESTION_TEXT = "QUESTION_TEXT"
        private const val QUESTION_ID = "QUESTION_ID"
    }
}