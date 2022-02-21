package com.trusov.sociallab.data.worker

import android.content.Context
import androidx.work.*
import com.trusov.sociallab.data.UStats
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

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<ScreenTimeSaver>()
                .build()
        }

        fun makePeriodicRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequest.Builder(
                ScreenTimeSaver::class.java,
                1L,
                TimeUnit.HOURS
            ).build()

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