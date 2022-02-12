package com.trusov.sociallab.presentation.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.trusov.sociallab.R
import com.trusov.sociallab.data.receiver.NotificationReceiver
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val context: Context
) : ContextWrapper(context) {

    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationView: RemoteViews

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    private fun createNotificationView(textOfQuestion: String) {

        fun createIntent(number: Int): Intent {
            return Intent(context, NotificationReceiver::class.java).apply {
                putExtra(NOTIFICATION_EXTRA_KEY, number)
            }
        }

        fun createPendingIntent(number: Int): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                number,
                createIntent(number),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        notificationView = RemoteViews(packageName, R.layout.custom_notification_layout)
        notificationView.apply {
            setTextViewText(R.id.tv_question_text, textOfQuestion)
            setOnClickPendingIntent(R.id.button_1, createPendingIntent(1))
            setOnClickPendingIntent(R.id.button_2, createPendingIntent(2))
            setOnClickPendingIntent(R.id.button_3, createPendingIntent(3))
            setOnClickPendingIntent(R.id.button_4, createPendingIntent(4))
            setOnClickPendingIntent(R.id.button_5, createPendingIntent(5))
        }
    }


    private fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_people_outline_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setCustomContentView(notificationView)
            .build()
    }

    fun showNotification(textOfQuestion: String) {
        createNotificationChannel()
        createNotificationView(textOfQuestion)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    companion object {
        private const val CHANNEL_ID = "QUESTION_CHANNEL_ID"
        private const val CHANNEL_NAME = "Question notification channel"
        private const val NOTIFICATION_EXTRA_KEY = "NOTIFICATION_EXTRA_KEY"
        private const val NOTIFICATION_ID = 1
    }
}