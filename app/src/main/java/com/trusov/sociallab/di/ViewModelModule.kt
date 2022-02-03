package com.trusov.sociallab.di

import androidx.lifecycle.ViewModel
import com.trusov.sociallab.presentation.fragment.log_in.LogInViewModel
import com.trusov.sociallab.presentation.fragment.sing_up.SignUpViewModel
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
}