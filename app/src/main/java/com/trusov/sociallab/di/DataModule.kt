package com.trusov.sociallab.di

import android.app.Application
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
    }
}