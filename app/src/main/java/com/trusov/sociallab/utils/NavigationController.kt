package com.trusov.sociallab.utils

interface NavigationController {
    fun launchWelcomeFragment()
    fun launchLoginFragment()
    fun launchSignUpFragment()
    fun launchResearchesFragment()
    fun launchMyResearchesFragment()
    fun launchAnswersFragment()
    fun launchStatisticsFragment()
    fun signOut()
}