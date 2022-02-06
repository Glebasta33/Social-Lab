package com.trusov.sociallab.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.databinding.ActivityMainBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.sing_up.SignUpFragment
import com.trusov.sociallab.presentation.fragment.welcome.WelcomeFragment
import com.trusov.sociallab.presentation.util.NavigationController
import com.trusov.sociallab.presentation.util.OnInputErrorListener
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnInputErrorListener, NavigationController {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var respondent: Respondent? = null

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
            checkAuth()
            withContext(Dispatchers.Main) {
                if (respondent != null) {
                    launchResearchesFragment()
                } else {
                    launchLoginFragment()
                }
            }
        }
    }

    override suspend fun checkAuth(): Respondent? {
        respondent = viewModel.getCurrentRespondent()
        return respondent
    }

    override fun launchWelcomeFragment() {
        replaceMainContainer(WelcomeFragment.newInstance())
    }

    override fun launchLoginFragment() {
        replaceMainContainer(LogInFragment.newInstance())
    }

    override fun launchSignUpFragment() {
        replaceMainContainer(SignUpFragment.newInstance())
    }

    override fun launchResearchesFragment() {
        replaceMainContainer(ResearchesFragment.newInstance(respondent))
    }

    private fun replaceMainContainer(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onErrorInput(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}

