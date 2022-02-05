package com.trusov.sociallab.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.ActivityMainBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.welcome.WelcomeFragment
import com.trusov.sociallab.presentation.util.OnInputErrorListener
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnInputErrorListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        launchWelcomeFragment()
        (application as SocialLabApp).component.inject(this)
        CoroutineScope(Dispatchers.IO).launch {
            val respondent = checkAuthentication()
            withContext(Dispatchers.Main) {
                launchRightFragment(respondent)
            }
        }
    }

    private suspend fun checkAuthentication(): Respondent? {
        return viewModel.getCurrentRespondent()
    }

    private fun launchRightFragment(respondent: Respondent?) {
        if (respondent == null) {
            launchLoginFragment()
        } else {
            launchResearchesFragment(respondent)
        }
    }

    override fun onErrorInput(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun launchWelcomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WelcomeFragment.newInstance())
            .commit()
    }

    private fun launchResearchesFragment(respondent: Respondent) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                ResearchesFragment.newInstance(respondent)
            )
            .commit()
    }

    private fun launchLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LogInFragment.newInstance())
            .commit()
    }

    companion object {
        private const val TAG = "MainActivityDebug"
    }
}

