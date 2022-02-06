package com.trusov.sociallab.presentation.fragment.my_researches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.databinding.MyResearchesFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import javax.inject.Inject

class MyResearchesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MyResearchesViewModel::class.java]
    }

    private var _binding: MyResearchesFragmentBinding? = null
    private val binding: MyResearchesFragmentBinding
        get() = _binding ?: throw RuntimeException("MyResearchesFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyResearchesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = MyResearchesFragment()
    }

}