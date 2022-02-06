package com.trusov.sociallab.di

import android.app.Application
import com.trusov.sociallab.presentation.activity.MainActivity
import com.trusov.sociallab.presentation.fragment.auth.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.auth.sing_up.SignUpFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(instance: LogInFragment)
    fun inject(instance: SignUpFragment)
    fun inject(instance: MainActivity)
    fun inject(instance: ResearchesFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}