package com.trusov.sociallab.presentation.fragment.researches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trusov.sociallab.R
import com.trusov.sociallab.databinding.ResearchesFragmentBinding


class ResearchesFragment : Fragment() {


    private var _binding: ResearchesFragmentBinding? = null
    private val binding: ResearchesFragmentBinding
        get() = _binding ?: throw RuntimeException("ResearchesFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResearchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance() = ResearchesFragment()

    }
}