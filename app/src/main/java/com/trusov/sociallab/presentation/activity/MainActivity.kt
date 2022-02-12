package com.trusov.sociallab.presentation.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.sociallab.R
import com.trusov.sociallab.SocialLabApp
import com.trusov.sociallab.data.receiver.NotificationReceiver
import com.trusov.sociallab.databinding.ActivityMainBinding
import com.trusov.sociallab.di.ViewModelFactory
import com.trusov.sociallab.presentation.fragment.answers.AnswersFragment
import com.trusov.sociallab.presentation.fragment.auth.log_in.LogInFragment
import com.trusov.sociallab.presentation.fragment.auth.sing_up.SignUpFragment
import com.trusov.sociallab.presentation.fragment.auth.welcome.WelcomeFragment
import com.trusov.sociallab.presentation.fragment.my_researches.MyResearchesFragment
import com.trusov.sociallab.presentation.fragment.researches.ResearchesFragment
import com.trusov.sociallab.presentation.fragment.statistics.StatisticsFragment
import com.trusov.sociallab.presentation.util.NavigationController
import com.trusov.sociallab.presentation.util.NotificationController
import com.trusov.sociallab.presentation.util.OnInputErrorListener
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnInputErrorListener, NavigationController,
    NotificationController {

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
        launchWelcomeFragment()
        (application as SocialLabApp).component.inject(this)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_all_researches -> launchResearchesFragment()
            R.id.action_my_researches -> launchMyResearchesFragment()
            R.id.action_answers -> launchAnswersFragment()
            R.id.action_statistics -> launchStatisticsFragment()
            R.id.action_sing_out -> {
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
    }

    override fun launchMyResearchesFragment() {
        replaceMainContainer(MyResearchesFragment.newInstance())
        changeToolbarContent(R.string.my_researches, R.drawable.ic_my_researches)
    }

    override fun launchAnswersFragment() {
        replaceMainContainer(AnswersFragment.newInstance())
        changeToolbarContent(R.string.my_answers, R.drawable.ic_answers)
    }

    override fun launchStatisticsFragment() {
        replaceMainContainer(StatisticsFragment.newInstance())
        changeToolbarContent(R.string.my_statistics, R.drawable.ic_statistics)
    }

    private fun changeToolbarContent(titleContent: Int, iconId: Int) {
        binding.toolbar.apply {
            title = resources.getString(titleContent)
            navigationIcon = ContextCompat.getDrawable(this@MainActivity, iconId)
        }
    }

    override fun signOut() {
        binding.toolbar.isGone = true
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

    override fun showNotification() {

        fun createIntent(number: Int): Intent {
            return Intent(this, NotificationReceiver::class.java).apply {
                putExtra("Notification", number)
            }
        }

        fun createPendingIntent(number: Int): PendingIntent {
            return PendingIntent.getBroadcast(
                this,
                number,
                createIntent(number),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationView = RemoteViews(packageName, R.layout.custom_notification_layout)
        notificationView.apply {
            setOnClickPendingIntent(R.id.button_1, createPendingIntent(1))
            setOnClickPendingIntent(R.id.button_2, createPendingIntent(2))
            setOnClickPendingIntent(R.id.button_3, createPendingIntent(3))
            setOnClickPendingIntent(R.id.button_4, createPendingIntent(4))
            setOnClickPendingIntent(R.id.button_5, createPendingIntent(5))
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "MY_CHANNEL_ID",
                "MY_CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "MY_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_people_outline_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setCustomContentView(notificationView)
            .build()

        notificationManager.notify(1, notification)
    }

}

