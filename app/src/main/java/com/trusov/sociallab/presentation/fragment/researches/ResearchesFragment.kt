package com.trusov.sociallab.presentation.fragment.researches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.ResearchesFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment
import com.trusov.sociallab.presentation.util.NavigationController
import javax.inject.Inject


class ResearchesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ResearchesViewModel::class.java]
    }

    private var _binding: ResearchesFragmentBinding? = null
    private val binding: ResearchesFragmentBinding
        get() = _binding ?: throw RuntimeException("ResearchesFragmentBinding == null")

    private lateinit var respondentArg: Respondent
    private lateinit var navigationController: NavigationController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationController) {
            navigationController = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as SocialLabApp).component.inject(this)
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
    ): View {
        _binding = ResearchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTest.text = respondentArg.toString()
        binding.buttonSignOut.setOnClickListener {
            viewModel.signOut()
            navigationController.launchLoginFragment()
        }
    }

    companion object {
        private const val RESPONDENT_KEY = "RESPONDENT_KEY"
        fun newInstance(respondent: Respondent?): ResearchesFragment {
            return ResearchesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESPONDENT_KEY, respondent)
                }
            }
        }

    }
}