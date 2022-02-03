package com.trusov.sociallab.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.trusov.sociallab.R
import com.trusov.sociallab.databinding.ActivityMainBinding
import com.trusov.sociallab.domain.entity.Respondent
import com.trusov.sociallab.presentation.fragment.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.welcome.WelcomeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), LogInFragment.OnErrorLoginListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val respondent: Respondent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, WelcomeFragment.newInstance())
                .commit()
            checkAuthentication()
            if (respondent == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, LogInFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, ResearchesFragment.newInstance(respondent))
                    .commit()
            }
        }
    }

    private suspend fun checkAuthentication() {
        delay(3000)
    }

    override fun onErrorLogin(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}

