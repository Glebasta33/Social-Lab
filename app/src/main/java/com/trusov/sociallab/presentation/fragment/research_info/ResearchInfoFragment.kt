package com.trusov.sociallab.presentation.fragment.research_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.ResearchInfoFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.domain.entity.Research
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
        (activity?.application as SocialLabApp).component.inject(this)
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        arguments?.let {
            research =
                it.getParcelable(RESEARCH_KEY) ?: throw RuntimeException("research == null")
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
        viewModel.getResearchId(research.id).observeForever { research ->
            with(binding) {
                tvTitle.text = research.topic
                tvDescription.text = research.description
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

    private fun ResearchInfoFragmentBinding.setButtonView(isRegistered: Boolean) {
        buttonRegisterToResearch.apply {
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