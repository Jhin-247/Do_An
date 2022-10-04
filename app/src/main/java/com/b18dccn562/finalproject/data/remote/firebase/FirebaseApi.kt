package com.b18dccn562.finalproject.data.remote.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseApi {
    private val mFirebaseAuth = Firebase.auth
    suspend fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    suspend fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    suspend fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    suspend fun logOut(authStateListener: AuthStateListener) {
        mFirebaseAuth.addAuthStateListener(authStateListener)
        mFirebaseAuth.signOut()

    }

}