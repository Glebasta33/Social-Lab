package com.trusov.sociallab.data.worker

import android.content.Context
import android.util.Log
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
        Log.d("ScreenTimeSaverTag", "ScreenTimeSaver: running")
        usageStats.saveCurrentTotalScreenTime()
        return Result.success()
    }

    companion object {
        const val NAME = "ScreenTimeSaver"

        fun makePeriodicRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequest.Builder(
                ScreenTimeSaver::class.java,
                15,
                TimeUnit.SECONDS
            )
                .addTag("ScreenTimeSaver")
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