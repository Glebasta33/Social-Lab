package com.trusov.sociallab.feature_statistics.data.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.worker.SubWorkerFactory
import com.trusov.sociallab.feature_statistics.data.source.UStats
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScreenTimeSaver(
    context: Context,
    workerParameters: WorkerParameters,
    private val auth: FirebaseAuth,
    private val firebase: FirebaseFirestore
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val usageStats = UStats(applicationContext as Application, auth, firebase)
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
        private val auth: FirebaseAuth,
        private val firebase: FirebaseFirestore
    ) : SubWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return ScreenTimeSaver(
                context,
                workerParameters,
                auth,
                firebase
            )
        }

    }
}