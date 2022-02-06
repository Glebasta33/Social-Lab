package com.trusov.sociallab.presentation.util

import com.trusov.sociallab.domain.entity.Respondent

interface NavigationController {
    suspend fun checkAuth(): Respondent?
    fun launchWelcomeFragment()
    fun launchLoginFragment()
    fun launchSignUpFragment()
    fun launchResearchesFragment()
    fun launchMyResearchesFragment()
    fun launchAnswersFragment()
    fun launchStatisticsFragment()
    fun signOut()
}