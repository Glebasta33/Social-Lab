package com.trusov.sociallab.di.module.view_model

import com.trusov.sociallab.feature_survey.data.repository.SurveyRepositoryImpl
import com.trusov.sociallab.feature_survey.domain.repository.SurveyRepository
import dagger.Binds
import dagger.Module

@Module
interface SurveyModule {
    @Binds
    fun bindSurveyRepository(impl: SurveyRepositoryImpl): SurveyRepository
}