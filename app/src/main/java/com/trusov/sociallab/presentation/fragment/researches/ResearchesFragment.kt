package com.trusov.sociallab.presentation.fragment.researches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trusov.sociallab.databinding.ResearchesFragmentBinding
import com.trusov.sociallab.domain.entity.Respondent


class ResearchesFragment : Fragment() {

    private var _binding: ResearchesFragmentBinding? = null
    private val binding: ResearchesFragmentBinding
        get() = _binding ?: throw RuntimeException("ResearchesFragmentBinding == null")
    private lateinit var respondentArg: Respondent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        arguments?.let {
            respondentArg =
                it.getParcelable(RESPONDENT_KEY) ?: throw RuntimeException("respondentArg == null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResearchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTest.text = respondentArg.toString()
    }

    companion object {
        private const val RESPONDENT_KEY = "RESPONDENT_KEY"

        fun newInstance(respondent: Respondent): ResearchesFragment {
            return ResearchesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESPONDENT_KEY, respondent)
                }
            }
        }

    }
}