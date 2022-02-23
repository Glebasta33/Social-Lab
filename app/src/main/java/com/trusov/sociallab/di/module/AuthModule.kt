package com.trusov.sociallab.di.module

import com.trusov.sociallab.feature_auth.data.repository.AuthRepositoryImpl
import com.trusov.sociallab.feature_auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}