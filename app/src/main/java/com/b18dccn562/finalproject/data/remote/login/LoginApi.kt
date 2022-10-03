package com.b18dccn562.finalproject.data.remote.login

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginApi {

    suspend fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        val mFirebaseAuth = Firebase.auth
        mFirebaseAuth.currentUser
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    suspend fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        val mFirebaseAuth = Firebase.auth
        mFirebaseAuth.currentUser
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    suspend fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

}