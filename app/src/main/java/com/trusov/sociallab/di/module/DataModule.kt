package com.trusov.sociallab.di.module

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trusov.sociallab.feature_statistics.data.repository.StatisticsRepositoryImpl
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.feature_statistics.domain.repository.StatisticsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: StatisticsRepositoryImpl): StatisticsRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return Firebase.firestore
        }

        @Provides
        @ApplicationScope
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }

        @Provides
        fun provideUsageStatsManager(application: Application): UsageStatsManager {
            return application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        }

    }
}