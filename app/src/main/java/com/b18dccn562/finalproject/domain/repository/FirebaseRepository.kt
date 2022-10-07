package com.b18dccn562.finalproject.domain.repository

import android.net.Uri
import com.b18dccn562.finalproject.domain.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ValueEventListener

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

    suspend fun saveDataToRealtimeDatabase(
        document: String,
        data: User,
        completeListener: OnCompleteListener<Void?>
    )

    suspend fun uploadImage(
        mImageUri: Uri,
        completeListener: OnCompleteListener<Void>
    )

    suspend fun loadAndObserveUserInfo(listener: ValueEventListener)

    suspend fun getUserImage(
        successListener: OnSuccessListener<ByteArray>,
        failureListener: OnFailureListener
    )
}