package com.trusov.sociallab.di.module

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trusov.sociallab.data.RepositoryImpl
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

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