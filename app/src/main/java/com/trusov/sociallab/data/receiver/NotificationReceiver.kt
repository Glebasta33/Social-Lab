package com.trusov.sociallab.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import javax.inject.Inject

class NotificationReceiver @Inject constructor() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiverTag", "${intent.getIntExtra(NOTIFICATION_EXTRA_KEY, -1)}")

    }

    companion object {
        private const val NOTIFICATION_EXTRA_KEY = "NOTIFICATION_EXTRA_KEY"
    }
}