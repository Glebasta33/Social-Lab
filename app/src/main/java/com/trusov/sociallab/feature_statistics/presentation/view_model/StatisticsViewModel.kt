package com.trusov.sociallab.feature_statistics.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime
import com.trusov.sociallab.feature_statistics.domain.usa_case.CheckUsageStatsPermissionUseCase
import com.trusov.sociallab.feature_statistics.domain.usa_case.GetListOfScreenTimeUseCase
import com.trusov.sociallab.feature_statistics.domain.usa_case.GetTotalScreenTimeUseCase
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val checkUsageStatsPermissionUseCase: CheckUsageStatsPermissionUseCase,
    private val getListOfScreenTimeUseCase: GetListOfScreenTimeUseCase,
    private val getTotalScreenTimeUseCase: GetTotalScreenTimeUseCase
) : ViewModel() {

    fun checkPermission() = checkUsageStatsPermissionUseCase()

    private val _list = MutableLiveData<List<AppScreenTime>>()
    val list: LiveData<List<AppScreenTime>> = _list

    private val _total = MutableLiveData<String>()
    val total: LiveData<String> = _total

    fun shopScreenTime() {
        val list = getListOfScreenTimeUseCase()
        val sortedList = list.sortedWith(
            compareBy(
                { it.hours },
                { it.minutes },
                { it.seconds })
        ).reversed()
        _list.value = sortedList
        _total.value = getTotalScreenTimeUseCase().formattedTime()
        Log.d("StatisticsViewModel", getTotalScreenTimeUseCase().formattedTime())
    }
}