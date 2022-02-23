package com.trusov.sociallab

import android.app.Application
import androidx.work.Configuration
import com.trusov.sociallab.data.worker.AppWorkerFactory
import com.trusov.sociallab.di.DaggerApplicationComponent
import javax.inject.Inject

class SocialLabApp : Application(), Configuration.Provider {

    @Inject
    lateinit var appWorkerFactory: AppWorkerFactory

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
            .setWorkerFactory(appWorkerFactory)
            .build()
    }


}