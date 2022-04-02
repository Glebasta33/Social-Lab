package com.trusov.sociallab.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.App
import com.trusov.sociallab.databinding.ActivityMainBinding
import com.trusov.sociallab.di.module.view_model.ViewModelFactory
import com.trusov.sociallab.feature_survey.presentation.fragment.AnswersFragment
import com.trusov.sociallab.feature_auth.presentation.fragment.LogInFragment
import com.trusov.sociallab.feature_auth.presentation.fragment.SignUpFragment
import com.trusov.sociallab.feature_auth.presentation.fragment.WelcomeFragment
import com.trusov.sociallab.feature_researches.presentation.fragment.ResearchesFragment
import com.trusov.sociallab.feature_statistics.presentation.fragment.StatisticsFragment
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnInputErrorListener, NavigationController {

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
        setActionBar()
        setBottomNavigation()
        launchWelcomeFragment()
        (application as App).component.inject(this)
        CoroutineScope(Dispatchers.IO).launch {
            val isAuthenticated = viewModel.getCurrentUser() != null
            withContext(Dispatchers.Main) {
                if (isAuthenticated) {
                    launchResearchesFragment()
                } else {
                    launchLoginFragment()
                }
            }
        }
    }

    private fun setActionBar() {
        binding.toolbar.apply {
            setSupportActionBar(this)
            setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
        }
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.mi_researches -> launchResearchesFragment()
                R.id.mi_answers -> launchAnswersFragment()
                R.id.mi_statistics -> launchStatisticsFragment()
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_sing_out -> {
                signOut()
                launchLoginFragment()
            }
        }
        return true
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
        replaceMainContainer(ResearchesFragment.newInstance())
        changeToolbarContent(R.string.researches, R.drawable.ic_researches)
        binding.toolbar.isGone = false
        binding.bottomNavigationView.isGone = false
    }

    override fun launchAnswersFragment() {
        replaceMainContainer(AnswersFragment.newInstance())
        changeToolbarContent(R.string.answers, R.drawable.ic_answers)
    }

    override fun launchStatisticsFragment() {
        replaceMainContainer(StatisticsFragment.newInstance())
        changeToolbarContent(R.string.statistics, R.drawable.ic_statistics)
    }

    private fun changeToolbarContent(titleContent: Int, iconId: Int) {
        binding.toolbar.apply {
            title = resources.getString(titleContent)
            navigationIcon = ContextCompat.getDrawable(this@MainActivity, iconId)
        }
    }

    override fun signOut() {
        binding.toolbar.isGone = true
        binding.bottomNavigationView.isGone = true
        viewModel.signOut()
    }

    private fun replaceMainContainer(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onErrorInput(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

