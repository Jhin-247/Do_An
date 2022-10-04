package com.b18dccn562.finalproject.domain.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    )

    suspend fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    )

    suspend fun getUser(): FirebaseUser?

    suspend fun logOut(authStateListener: AuthStateListener)


}