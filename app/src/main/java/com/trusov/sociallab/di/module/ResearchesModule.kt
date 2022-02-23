package com.trusov.sociallab.di.module

import com.trusov.sociallab.researches.data.repository.ResearchesRepositoryImpl
import com.trusov.sociallab.researches.domain.repository.ResearchesRepository
import dagger.Binds
import dagger.Module

@Module
interface ResearchesModule {
    @Binds
    fun bindResearchesRepository(impl: ResearchesRepositoryImpl): ResearchesRepository
}