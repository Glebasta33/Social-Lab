package com.trusov.sociallab.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trusov.sociallab.data.RepositoryImpl
import com.trusov.sociallab.data.database.AppDatabase
import com.trusov.sociallab.data.database.EnterDao
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
        fun provideEnterDao(application: Application): EnterDao {
            return AppDatabase.getInstance(application).enterDao()
        }

        @Provides
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return Firebase.firestore
        }

        @Provides
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }
    }
}