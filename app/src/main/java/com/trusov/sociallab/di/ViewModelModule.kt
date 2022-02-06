package com.trusov.sociallab.di

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.presentation.activity.MainActivityViewModel
import com.trusov.sociallab.presentation.fragment.auth.log_in.LogInViewModel
import com.trusov.sociallab.presentation.fragment.researches.ResearchesViewModel
import com.trusov.sociallab.presentation.fragment.auth.sing_up.SignUpViewModel
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
}