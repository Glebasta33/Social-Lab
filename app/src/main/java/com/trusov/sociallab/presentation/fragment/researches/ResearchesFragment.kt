package com.trusov.sociallab.presentation.fragment.researches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.App
import com.trusov.sociallab.databinding.ResearchesFragmentBinding
import com.trusov.sociallab.di.module.view_model.ViewModelFactory
import com.trusov.sociallab.presentation.fragment.research_info.ResearchInfoFragment
import com.trusov.sociallab.utils.NavigationController
import javax.inject.Inject


class ResearchesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var researchesListAdapter: ResearchesListAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ResearchesViewModel::class.java]
    }

    private var _binding: ResearchesFragmentBinding? = null
    private val binding: ResearchesFragmentBinding
        get() = _binding ?: throw RuntimeException("ResearchesFragmentBinding == null")

    private lateinit var navigationController: NavigationController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).component.inject(this)
        if (context is NavigationController) {
            navigationController = context
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
        binding.rvResearches.adapter = researchesListAdapter
        viewModel.getListOfResearches().observe(viewLifecycleOwner) {
            researchesListAdapter.submitList(it?.toMutableList())
        }
        researchesListAdapter.onResearchItemClickListener = { research ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ResearchInfoFragment.newInstance(research))
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): ResearchesFragment {
            return ResearchesFragment()
        }
    }
}