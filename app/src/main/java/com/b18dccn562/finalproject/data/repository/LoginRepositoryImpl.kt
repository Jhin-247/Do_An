package com.b18dccn562.finalproject.data.repository

import com.b18dccn562.finalproject.data.remote.login.LoginApi
import com.b18dccn562.finalproject.domain.repository.LoginRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        loginApi.signIn(email, password, completeListener)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        loginApi.signUp(email, password, completeListener)
    }

    override suspend fun getUser(): FirebaseUser? {
        return loginApi.getUser()
    }
}