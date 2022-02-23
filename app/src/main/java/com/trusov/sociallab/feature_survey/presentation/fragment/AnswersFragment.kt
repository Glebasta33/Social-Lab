package com.trusov.sociallab.feature_survey.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.App
import com.trusov.sociallab.databinding.AnswersFragmentBinding
import com.trusov.sociallab.di.module.view_model.ViewModelFactory
import com.trusov.sociallab.feature_survey.domain.entity.AnswerExtended
import com.trusov.sociallab.feature_survey.presentation.view_model.AnswersViewModel
import com.trusov.sociallab.feature_survey.presentation.adapter.AnswersListAdapter
import javax.inject.Inject

class AnswersFragment : Fragment() {

    @Inject
    lateinit var answersAdapter: AnswersListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AnswersViewModel::class.java]
    }

    private var _binding: AnswersFragmentBinding? = null
    private val binding: AnswersFragmentBinding
        get() = _binding ?: throw RuntimeException("AnswersFragmentBinding == null")


    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnswersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAnswers.adapter = answersAdapter
        viewModel.answers.observe(viewLifecycleOwner) {
            answersAdapter.submitList(it?.toMutableList() ?: listOf(AnswerExtended("id", "id", 4, "Title", "Text of question")))
        }
    }

    companion object {
        fun newInstance() = AnswersFragment()
    }
}