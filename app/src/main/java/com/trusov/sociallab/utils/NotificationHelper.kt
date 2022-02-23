package com.trusov.sociallab.utils

import android.app.*
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.trusov.sociallab.R
import com.trusov.sociallab.data.receiver.NotificationReceiver
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val application: Application
) : ContextWrapper(application) {

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


    private fun createNotificationView(textOfQuestion: String, questionId: String) {

        fun createIntent(number: Int): Intent {
            return Intent(application, NotificationReceiver::class.java).apply {
                putExtra(NUMBER_OF_ANSWER, number)
                putExtra(QUESTION_ID, questionId)
            }
        }

        fun createPendingIntent(number: Int): PendingIntent {
            return PendingIntent.getBroadcast(
                application,
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

    fun showNotification(textOfQuestion: String, questionId: String) {
        createNotificationChannel()
        createNotificationView(textOfQuestion, questionId)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    companion object {
        private const val CHANNEL_ID = "QUESTION_CHANNEL_ID"
        private const val CHANNEL_NAME = "Question notification channel"
        private const val NUMBER_OF_ANSWER = "NUMBER_OF_ANSWER"
        private const val QUESTION_ID = "QUESTION_ID"
        private const val NOTIFICATION_ID = 1
    }
}