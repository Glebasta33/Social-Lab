package com.trusov.sociallab.presentation.fragment.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.databinding.AnswersFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import javax.inject.Inject

class AnswersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AnswersViewModel::class.java]
    }

    private var _binding: AnswersFragmentBinding? = null
    private val binding: AnswersFragmentBinding
        get() = _binding ?: throw RuntimeException("AnswersFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnswersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = AnswersFragment()
    }
}