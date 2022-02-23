package com.trusov.sociallab.feature_auth.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.trusov.sociallab.feature_auth.domain.repository.AuthRepository
import com.trusov.sociallab.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override fun signUp(login: String, password: String) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d(TAG, "task.isSuccessful $this: $respondent")
                } else {
                    Log.d(TAG, "!task.isSuccessful $this: ${task.exception}")
                }
            }
    }

    override fun logIn(login: String, password: String) {
        auth.signInWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    Log.d(TAG, "$this: $respondent")
                } else {
                    Log.d(TAG, "$this: ${task.exception}")
                }
            }
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        auth.currentUser?.let {
            return it
        }
        return null
    }

    override fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val TAG = "AuthRepositoryImplTag"
    }
}