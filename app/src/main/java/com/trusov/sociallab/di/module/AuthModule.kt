package com.trusov.sociallab.di.module

import com.trusov.sociallab.auth.data.repository.AuthRepositoryImpl
import com.trusov.sociallab.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}