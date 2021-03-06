package com.trusov.sociallab.di.module.view_model

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.presentation.MainActivityViewModel
import com.trusov.sociallab.feature_survey.presentation.view_model.AnswersViewModel
import com.trusov.sociallab.feature_auth.presentation.view_model.LogInViewModel
import com.trusov.sociallab.feature_researches.presentation.view_model.ResearchesViewModel
import com.trusov.sociallab.feature_auth.presentation.view_model.SignUpViewModel
import com.trusov.sociallab.di.key.ViewModelKey
import com.trusov.sociallab.feature_researches.presentation.view_model.ResearchInfoViewModel
import com.trusov.sociallab.feature_statistics.presentation.view_model.StatisticsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    @Binds
    fun bindLogInViewModel(viewModel: LogInViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    @Binds
    fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    @Binds
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ResearchesViewModel::class)
    @Binds
    fun bindResearchesViewModel(viewModel: ResearchesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AnswersViewModel::class)
    @Binds
    fun bindAnswersViewModel(viewModel: AnswersViewModel): ViewModel

    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    @Binds
    fun bindStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ResearchInfoViewModel::class)
    @Binds
    fun bindResearchInfoViewModel(viewModel: ResearchInfoViewModel): ViewModel
}