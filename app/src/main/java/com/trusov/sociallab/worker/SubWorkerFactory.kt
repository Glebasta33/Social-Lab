package com.trusov.sociallab.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface SubWorkerFactory {
    fun create(
        context: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker
}