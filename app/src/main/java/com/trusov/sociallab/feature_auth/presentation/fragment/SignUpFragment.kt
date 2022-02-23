package com.trusov.sociallab.feature_auth.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.App
import com.trusov.sociallab.databinding.SingUpFragmentBinding
import com.trusov.sociallab.di.module.view_model.ViewModelFactory
import com.trusov.sociallab.core.presentation.NavigationController
import com.trusov.sociallab.core.presentation.OnInputErrorListener
import com.trusov.sociallab.feature_auth.presentation.view_model.SignUpViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]
    }
    private lateinit var onInputErrorListener: OnInputErrorListener
    private lateinit var navigationController: NavigationController

    private var _binding: SingUpFragmentBinding? = null
    private val binding: SingUpFragmentBinding
        get() = _binding ?: throw RuntimeException("SingUpFragmentBinding == null")

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
        if (context is OnInputErrorListener) {
            onInputErrorListener = context
        }
        if (context is NavigationController) {
            navigationController = context
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
        with(binding) {
            tvToLogIn.setOnClickListener {
                navigationController.launchLoginFragment()
            }
            buttonSignUp.setOnClickListener {
                signUp()
                checkRegistration()
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            onInputErrorListener.onErrorInput(it)
        }
    }

    private fun SingUpFragmentBinding.signUp() {
        val login = etEmail.text.toString()
        val password1 = etPassword.text.toString()
        val password2 = etPassword2.text.toString()
        viewModel.singUp(login, password1, password2, checkBox.isChecked)
    }

    private fun checkRegistration() {
        viewModel.readyToClose.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    setProgressBarVisibility(VISIBLE)
                }
                val isAuthenticated = viewModel.getCurrentUser() != null
                withContext(Dispatchers.Main) {
                    if (isAuthenticated) {
                        navigationController.launchResearchesFragment()
                    } else {
                        viewModel.showSignUpFailedMessage()
                    }
                    setProgressBarVisibility(INVISIBLE)
                }
            }
        }
    }

    private suspend fun setProgressBarVisibility(visibility: Int) {
        when (visibility) {
            VISIBLE -> {
                binding.progress.isVisible = true
                delay(2000L)
            }
            INVISIBLE -> {
                binding.progress.isVisible = false
            }
        }
    }

    companion object {
        private const val VISIBLE = 200
        private const val INVISIBLE = 400
        fun newInstance() = SignUpFragment()
    }

}