package com.trusov.sociallab.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.trusov.sociallab.feature_statistics.data.worker.ScreenTimeSaver
import com.trusov.sociallab.feature_survey.data.worker.QuestionsWorker
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
            ScreenTimeSaver::class.qualifiedName -> {
                val subWorkerFactory = workerProviders[ScreenTimeSaver::class.java]?.get()
                return subWorkerFactory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}