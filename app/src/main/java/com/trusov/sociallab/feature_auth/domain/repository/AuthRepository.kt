package com.trusov.sociallab.feature_auth.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun signUp(login: String, password: String)
    fun logIn(login: String, password: String)
    suspend fun getCurrentUser(): FirebaseUser?
    fun signOut()
}