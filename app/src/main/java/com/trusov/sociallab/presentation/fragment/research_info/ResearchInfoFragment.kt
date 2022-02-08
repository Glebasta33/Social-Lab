package com.trusov.sociallab.presentation.fragment.research_info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.ResearchInfoFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.presentation.fragment.answers.AnswersViewModel
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
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

    private lateinit var researchId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as SocialLabApp).component.inject(this)
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        arguments?.let {
            researchId =
                it.getString(RESEARCH_ID) ?: throw RuntimeException("respondentArg == null")
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
        viewModel.getResearchId(researchId).observeForever { research ->
            with(binding) {
                tvTitle.text = research.topic
                tvDescription.text = research.description
                buttonRegisterToResearch.setOnClickListener {
                    buttonRegisterToResearch.text = "Отписаться"
                    buttonRegisterToResearch.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
        }
    }

    companion object {
        private const val RESEARCH_ID = "RESEARCH_ID"
        fun newInstance(researchId: String): ResearchInfoFragment {
            return ResearchInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(RESEARCH_ID, researchId)
                }
            }
        }
    }

}