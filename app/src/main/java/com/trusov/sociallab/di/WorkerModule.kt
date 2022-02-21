package com.trusov.sociallab.di

import com.trusov.sociallab.data.worker.QuestionsWorker
import com.trusov.sociallab.data.worker.SubWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(QuestionsWorker::class)
    fun bindQuestionWorker(worker: QuestionsWorker.Factory): SubWorkerFactory
}