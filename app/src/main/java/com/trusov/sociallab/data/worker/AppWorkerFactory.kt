package com.trusov.sociallab.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.presentation.util.NotificationHelper
import javax.inject.Inject
import javax.inject.Provider

class AppWorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<SubWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            QuestionsWorker::class.qualifiedName -> {
                val subWorkerFactory = workerProviders[QuestionsWorker::class.java]?.get()
                return subWorkerFactory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}