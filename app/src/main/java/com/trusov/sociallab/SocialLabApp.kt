package com.trusov.sociallab

import android.app.Application
import androidx.work.Configuration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trusov.sociallab.data.worker.QuestionsWorkerFactory
import com.trusov.sociallab.di.DaggerApplicationComponent
import com.trusov.sociallab.presentation.util.NotificationHelper

class SocialLabApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                QuestionsWorkerFactory(
                    Firebase.firestore,
                    NotificationHelper(this)
                )
            )
            .build()
    }


}