package com.trusov.sociallab.presentation.fragment.sing_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.databinding.SingUpFragmentBinding
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment

class SingUpFragment : Fragment() {

    private lateinit var viewModel: SingUpViewModel

    private var _binding: SingUpFragmentBinding? = null
    private val binding: SingUpFragmentBinding
        get() = _binding ?: throw RuntimeException("SingUpFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SingUpViewModel::class.java]
        with(binding) {
            tvToLogIn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, LogInFragment.newInstance())
                    .commit()
            }
        }
    }

    companion object {
        fun newInstance() = SingUpFragment()
    }

}