package com.b18dccn562.finalproject.data.repository

import com.b18dccn562.finalproject.data.remote.firebase.FirebaseApi
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseApi
) : FirebaseRepository {

    override suspend fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        firebaseApi.signIn(email, password, completeListener)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        firebaseApi.signUp(email, password, completeListener)
    }

    override suspend fun getUser(): FirebaseUser? {
        return firebaseApi.getUser()
    }

    override suspend fun logOut(authStateListener: AuthStateListener) {
        return firebaseApi.logOut(authStateListener)
    }
}