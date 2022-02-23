package com.trusov.sociallab.di.component

import android.app.Application
import com.trusov.sociallab.App
import com.trusov.sociallab.data.receiver.NotificationReceiver
import com.trusov.sociallab.presentation.activity.MainActivity
import com.trusov.sociallab.survey.presentation.AnswersFragment
import com.trusov.sociallab.auth.presentation.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.auth.presentation.SignUpFragment
import com.trusov.sociallab.di.module.AuthModule
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.di.module.DataModule
import com.trusov.sociallab.di.module.view_model.ViewModelModule
import com.trusov.sociallab.di.module.WorkerModule
import com.trusov.sociallab.di.module.view_model.SurveyModule
import com.trusov.sociallab.presentation.fragment.my_researches.MyResearchesFragment
import com.trusov.sociallab.presentation.fragment.research_info.ResearchInfoFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesListAdapter
import com.trusov.sociallab.presentation.fragment.statistics.StatisticsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class,
        AuthModule::class,
        SurveyModule::class
    ]
)
interface ApplicationComponent {

    fun inject(instance: LogInFragment)
    fun inject(instance: SignUpFragment)
    fun inject(instance: MainActivity)
    fun inject(instance: ResearchesFragment)
    fun inject(instance: MyResearchesFragment)
    fun inject(instance: AnswersFragment)
    fun inject(instance: StatisticsFragment)
    fun inject(instance: ResearchInfoFragment)
    fun inject(instance: ResearchesListAdapter)
    fun inject(instance: App)
    fun inject(instance: NotificationReceiver)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}