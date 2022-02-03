package com.trusov.sociallab.presentation.fragment.log_in

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.LogInFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.presentation.fragment.sing_up.SignUpFragment
import javax.inject.Inject

class LogInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LogInViewModel

    private var _binding: LogInFragmentBinding? = null
    private val binding: LogInFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    override fun onAttach(context: Context) {
        (activity?.application as SocialLabApp).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LogInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[LogInViewModel::class.java]
        with(binding){
            tvToSingUp.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, SignUpFragment.newInstance())
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = LogInFragment()
    }


}