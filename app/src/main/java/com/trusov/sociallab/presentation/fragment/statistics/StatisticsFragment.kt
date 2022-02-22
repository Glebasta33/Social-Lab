package com.trusov.sociallab.presentation.fragment.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.data.worker.ScreenTimeSaver
import com.trusov.sociallab.databinding.StatisticsFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StatisticsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
    }

    @Inject
    lateinit var screenTimeListAdapter: ScreenTimeListAdapter

    private var _binding: StatisticsFragmentBinding? = null
    private val binding: StatisticsFragmentBinding
        get() = _binding ?: throw RuntimeException("StatisticsFragmentBinding == null")

    override fun onAttach(context: Context) {
        (activity?.application as SocialLabApp).component.inject(this)
        if (viewModel.checkPermission()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            activity?.startActivity(intent)
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StatisticsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvScreenTime.adapter = screenTimeListAdapter
        viewModel.list.observe(viewLifecycleOwner) {
            screenTimeListAdapter.submitList(it)
        }
        viewModel.total.observe(viewLifecycleOwner) {
            binding.tvTotalScreenTime.text = it
        }
        lifecycleScope.launch {
            viewModel.shopScreenTime()
        }
        val workManager = WorkManager.getInstance(requireActivity().application)
        var delay = 0L
        binding.tvLabel.setOnClickListener {
            delay = getDelayToStart()
            workManager.enqueueUniquePeriodicWork(
                ScreenTimeSaver.NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                ScreenTimeSaver.makePeriodicRequest(delay)
            )
            Log.d("ScreenTimeSaverTag", "StatisticsFragment: clicked")
        }
        workManager.getWorkInfosByTagLiveData("ScreenTimeSaver")
            .observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) {
                    binding.tvLabel.text = "${it[0]?.state.toString()} \n $delay"
                }
            }
    }

    private fun getDelayToStart(): Long {
        val minFormat = SimpleDateFormat("mm")
        val secFormat = SimpleDateFormat("ss")
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        val currentMinutes = minFormat.format(currentTime).toString().toInt()
        val currentSeconds = secFormat.format(currentTime).toString().toInt()
        calendar.add(Calendar.HOUR_OF_DAY, +1)
        calendar.add(Calendar.MINUTE, -currentMinutes)
        calendar.add(Calendar.SECOND, -currentSeconds)

        val specificTimeToTrigger = calendar.timeInMillis

        return specificTimeToTrigger - currentTime
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}
