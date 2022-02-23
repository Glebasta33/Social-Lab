package com.trusov.sociallab.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.data.worker.QuestionsWorker
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.domain.entity.*
import com.trusov.sociallab.domain.repository.Repository
import com.trusov.sociallab.survey.domain.entity.Answer
import com.trusov.sociallab.survey.domain.entity.AnswerExtended
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.ArrayList

@ApplicationScope
class RepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val application: Application,
    private val usageStats: UStats
) : Repository {



    override fun getListOfScreenTime(): List<AppScreenTime> {
        return usageStats.getListOfScreenTime()
    }

    override fun getTotalScreenTime(): AppScreenTime {
        return usageStats.getTotalScreenTime()
    }

    override fun checkUsageStatsPermission(): Boolean {
        return usageStats.getUsageStatsList().isEmpty()
    }

    companion object {
        private const val TAG = "RepositoryImplTag"
    }
}