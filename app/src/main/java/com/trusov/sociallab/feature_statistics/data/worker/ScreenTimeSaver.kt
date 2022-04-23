package com.trusov.sociallab.feature_statistics.data.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.trusov.sociallab.worker.SubWorkerFactory
import com.trusov.sociallab.feature_statistics.data.source.UStats
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScreenTimeSaver(
    context: Context,
    workerParameters: WorkerParameters,
    private val usageStats: UStats
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        usageStats.saveCurrentTotalScreenTime()
        return Result.success()
    }

    companion object {
        const val NAME = "ScreenTimeSaver"

        fun makePeriodicRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequest.Builder(
                ScreenTimeSaver::class.java,
                15,
                TimeUnit.MINUTES
            )
                .build()
        }
    }

    class Factory @Inject constructor(
        private val usageStats: UStats
    ) : SubWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return ScreenTimeSaver(
                context,
                workerParameters,
                usageStats
            )
        }

    }
}