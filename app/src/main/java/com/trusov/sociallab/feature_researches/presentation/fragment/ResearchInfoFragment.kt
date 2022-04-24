package com.trusov.sociallab.feature_researches.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.App
import com.trusov.sociallab.databinding.ResearchInfoFragmentBinding
import com.trusov.sociallab.di.module.view_model.ViewModelFactory
import com.trusov.sociallab.feature_researches.domain.entity.Research
import com.trusov.sociallab.feature_researches.presentation.view_model.ResearchInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResearchInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ResearchInfoViewModel::class.java]
    }

    private var _binding: ResearchInfoFragmentBinding? = null
    private val binding: ResearchInfoFragmentBinding
        get() = _binding ?: throw RuntimeException("ResearchInfoFragmentBinding == null")

    private lateinit var research: Research

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            research = it.getParcelable(RESEARCH_KEY) ?: throw RuntimeException("research == null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResearchInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegisterToResearch.isGone = true
        viewModel.getResearchId(research.id).observe(viewLifecycleOwner) { research ->
            with(binding) {
                setResearchViews(research)
                CoroutineScope(Dispatchers.IO).launch {
                    val currentUserId = viewModel.getCurrentUser()?.uid
                    withContext(Dispatchers.Main) {
                        setButtonView(research.respondents.contains(currentUserId))
                    }
                }
                buttonRegisterToResearch.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val currentUserId = viewModel.getCurrentUser()?.uid
                        withContext(Dispatchers.Main) {
                            if (!research.respondents.contains(currentUserId)) {
                                viewModel.registerToResearch(this@ResearchInfoFragment.research.id)
                            } else {
                                viewModel.unregisterFromResearch(this@ResearchInfoFragment.research.id)
                            }
                            setButtonView(research.respondents.contains(currentUserId))
                        }
                    }

                }
            }
        }
    }

    private fun ResearchInfoFragmentBinding.setResearchViews(research: Research) {
        tvTitle.text = research.topic
        tvDescription.text = research.description
        tvAppointment.text = research.appointment
        tvSelection.text = research.respondents.size.toString()
    }

    private fun ResearchInfoFragmentBinding.setButtonView(isRegistered: Boolean) {
        buttonRegisterToResearch.apply {
            isGone = false
            if (isRegistered) {
                text = "Отписаться"
                buttonRegisterToResearch.setBackgroundColor(resources.getColor(R.color.red))
            } else {
                text = "Зарегистрироваться"
                buttonRegisterToResearch.setBackgroundColor(resources.getColor(R.color.grey))
            }
        }
    }

    companion object {
        private const val RESEARCH_KEY = "RESEARCH_KEY"

        fun newInstance(research: Research): ResearchInfoFragment {
            return ResearchInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESEARCH_KEY, research)
                }
            }
        }
    }

}