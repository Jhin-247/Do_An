package com.b18dccn562.finalproject.data.repository

import android.net.Uri
import com.b18dccn562.finalproject.data.remote.firebase.FirebaseApi
import com.b18dccn562.finalproject.domain.model.User
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ValueEventListener
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

    override suspend fun saveDataToRealtimeDatabase(
        document: String,
        data: User,
        completeListener: OnCompleteListener<Void?>
    ) {
        firebaseApi.saveUserInfoDataToRealtimeDatabase(
            document,
            data,
            completeListener
        )
    }

    override suspend fun uploadImage(mImageUri: Uri, completeListener: OnCompleteListener<Void>) {
        firebaseApi.uploadImage(
            mImageUri,
            completeListener
        )
    }

    override suspend fun loadAndObserveUserInfo(listener: ValueEventListener) {
        firebaseApi.loadAndObserveUserInfo(listener)
    }

    override suspend fun getUserImage(
        successListener: OnSuccessListener<ByteArray>,
        failureListener: OnFailureListener
    ) {
        firebaseApi.getUserImage(successListener, failureListener)
    }
}