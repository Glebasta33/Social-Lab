package com.trusov.sociallab.di.component

import android.app.Application
import com.trusov.sociallab.App
import com.trusov.sociallab.feature_survey.data.receiver.NotificationReceiver
import com.trusov.sociallab.core.presentation.MainActivity
import com.trusov.sociallab.feature_survey.presentation.fragment.AnswersFragment
import com.trusov.sociallab.feature_auth.presentation.fragment.LogInFragment
import com.trusov.sociallab.feature_researches.presentation.fragment.ResearchesFragment
import com.trusov.sociallab.feature_auth.presentation.fragment.SignUpFragment
import com.trusov.sociallab.di.module.AuthModule
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.di.module.DataModule
import com.trusov.sociallab.di.module.ResearchesModule
import com.trusov.sociallab.di.module.view_model.ViewModelModule
import com.trusov.sociallab.di.module.WorkerModule
import com.trusov.sociallab.di.module.view_model.SurveyModule
import com.trusov.sociallab.feature_researches.presentation.fragment.ResearchInfoFragment
import com.trusov.sociallab.feature_researches.presentation.adapter.ResearchesListAdapter
import com.trusov.sociallab.feature_statistics.presentation.fragment.StatisticsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class,
        AuthModule::class,
        SurveyModule::class,
        ResearchesModule::class
    ]
)
interface ApplicationComponent {

    fun inject(instance: LogInFragment)
    fun inject(instance: SignUpFragment)
    fun inject(instance: MainActivity)
    fun inject(instance: ResearchesFragment)
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