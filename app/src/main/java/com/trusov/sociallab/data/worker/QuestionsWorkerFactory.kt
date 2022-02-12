package com.trusov.sociallab.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.presentation.util.NotificationHelper

class QuestionsWorkerFactory(
    private val firebase: FirebaseFirestore,
    private val notificationHelper: NotificationHelper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return QuestionsWorker(
            appContext,
            workerParameters,
            firebase,
            notificationHelper
        )
    }
}