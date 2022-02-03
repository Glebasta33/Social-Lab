package com.trusov.sociallab.presentation.fragment.sing_up

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.SingUpFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.util.OnInputErrorListener
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SignUpViewModel
    private lateinit var onInputErrorListener: OnInputErrorListener

    private var _binding: SingUpFragmentBinding? = null
    private val binding: SingUpFragmentBinding
        get() = _binding ?: throw RuntimeException("SingUpFragmentBinding == null")

    override fun onAttach(context: Context) {
        (activity?.application as SocialLabApp).component.inject(this)
        super.onAttach(context)
        if (context is OnInputErrorListener) {
            onInputErrorListener = context
        } else {
            throw RuntimeException("Activity $context must implement onErrorLoginListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]
        with(binding) {
            tvToLogIn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, LogInFragment.newInstance())
                    .commit()
            }
            buttonSignUp.setOnClickListener {
                val login = etEmail.text.toString()
                val password1 = etPassword.text.toString()
                val password2 = etPassword2.text.toString()
                viewModel.singUp(login, password1, password2, checkBox.isChecked)
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            onInputErrorListener.onErrorInput(it)
        }

        viewModel.respondent.observe(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ResearchesFragment.newInstance(it))
                .commit()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }

}