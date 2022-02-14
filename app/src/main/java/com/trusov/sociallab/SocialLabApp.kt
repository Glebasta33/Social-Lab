package com.trusov.sociallab

import android.app.Application
import androidx.work.Configuration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trusov.sociallab.data.worker.QuestionsWorkerFactory
import com.trusov.sociallab.di.DaggerApplicationComponent
import com.trusov.sociallab.presentation.util.NotificationHelper
import javax.inject.Inject

class SocialLabApp : Application(), Configuration.Provider {

    @Inject
    lateinit var questionsWorkerFactory: QuestionsWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(questionsWorkerFactory)
            .build()
    }


}