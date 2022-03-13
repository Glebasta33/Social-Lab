package com.trusov.sociallab.presentation

interface NavigationController {
    fun launchWelcomeFragment()
    fun launchLoginFragment()
    fun launchSignUpFragment()
    fun launchResearchesFragment()
    fun launchAnswersFragment()
    fun launchStatisticsFragment()
    fun signOut()
}