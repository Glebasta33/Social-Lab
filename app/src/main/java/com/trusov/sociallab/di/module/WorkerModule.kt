package com.trusov.sociallab.di.module

import com.trusov.sociallab.feature_survey.data.worker.QuestionsWorker
import com.trusov.sociallab.feature_statistics.data.worker.ScreenTimeSaver
import com.trusov.sociallab.worker.SubWorkerFactory
import com.trusov.sociallab.di.key.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(QuestionsWorker::class)
    fun bindQuestionWorker(worker: QuestionsWorker.Factory): SubWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(ScreenTimeSaver::class)
    fun bindScreenTimeSaver(worker: ScreenTimeSaver.Factory): SubWorkerFactory

}