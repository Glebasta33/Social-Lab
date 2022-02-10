package com.trusov.sociallab.presentation.fragment.auth.log_in

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.LogInFragmentBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.presentation.util.NavigationController
import com.trusov.sociallab.presentation.util.OnInputErrorListener
import kotlinx.coroutines.*
import javax.inject.Inject

class LogInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LogInViewModel::class.java]
    }
    private lateinit var onInputErrorListener: OnInputErrorListener
    private lateinit var navigationController: NavigationController

    private var _binding: LogInFragmentBinding? = null
    private val binding: LogInFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    override fun onAttach(context: Context) {
        (activity?.application as SocialLabApp).component.inject(this)
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
        _binding = LogInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvToSingUp.setOnClickListener {
                navigationController.launchSignUpFragment()
            }
            buttonLogIn.setOnClickListener {
                login()
                checkLogin()
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            onInputErrorListener.onErrorInput(it)
        }
    }

    private fun LogInFragmentBinding.login() {
        val login = etEmail.text.toString()
        val password = etPassword.text.toString()
        viewModel.logIn(login, password)
    }

    private fun checkLogin() {
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
                        viewModel.showWrongInputsMessage()
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
        fun newInstance() = LogInFragment()
    }


}