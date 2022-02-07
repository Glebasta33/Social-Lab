package com.trusov.sociallab.di

import android.app.Application
import com.trusov.sociallab.presentation.activity.MainActivity
import com.trusov.sociallab.presentation.fragment.answers.AnswersFragment
import com.trusov.sociallab.presentation.fragment.auth.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.auth.sing_up.SignUpFragment
import com.trusov.sociallab.presentation.fragment.my_researches.MyResearchesFragment
import com.trusov.sociallab.presentation.fragment.research_info.ResearchInfoFragment
import com.trusov.sociallab.presentation.fragment.statistics.StatisticsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(instance: LogInFragment)
    fun inject(instance: SignUpFragment)
    fun inject(instance: MainActivity)
    fun inject(instance: ResearchesFragment)
    fun inject(instance: MyResearchesFragment)
    fun inject(instance: AnswersFragment)
    fun inject(instance: StatisticsFragment)
    fun inject(instance: ResearchInfoFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}