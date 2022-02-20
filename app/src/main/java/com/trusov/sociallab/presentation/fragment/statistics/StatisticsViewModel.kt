package com.trusov.sociallab.presentation.fragment.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trusov.sociallab.domain.entity.ScreenTime
import com.trusov.sociallab.domain.use_case.usage_stats.CheckUsageStatsPermissionUseCase
import com.trusov.sociallab.domain.use_case.usage_stats.GetListOfScreenTimeUseCase
import com.trusov.sociallab.domain.use_case.usage_stats.GetTotalScreenTimeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val checkUsageStatsPermissionUseCase: CheckUsageStatsPermissionUseCase,
    private val getListOfScreenTimeUseCase: GetListOfScreenTimeUseCase,
    private val getTotalScreenTimeUseCase: GetTotalScreenTimeUseCase
) : ViewModel() {

    fun checkPermission() = checkUsageStatsPermissionUseCase()

    private val _list = MutableLiveData<List<ScreenTime>>()
    val list: LiveData<List<ScreenTime>> = _list

    private val _total = MutableLiveData<String>()
    val total: LiveData<String> = _total

    fun shopScreenTime() {
        viewModelScope.launch {
            val list = getListOfScreenTimeUseCase()
            val sortedList = list.sortedWith(
                compareBy(
                    { it.hours },
                    { it.minutes },
                    { it.seconds })
            ).reversed()
            _list.value = sortedList
            _total.value = getTotalScreenTimeUseCase().formattedTime()
        }
    }
}