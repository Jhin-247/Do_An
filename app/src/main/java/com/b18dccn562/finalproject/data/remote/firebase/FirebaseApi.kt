package com.b18dccn562.finalproject.data.remote.firebase

import android.net.Uri
import com.b18dccn562.finalproject.common.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class FirebaseApi {

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance(Constants.REALTIME_DATABASE_URL).reference

    private val storage = FirebaseStorage.getInstance().reference

    fun signIn(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        val mFirebaseAuth = Firebase.auth
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    fun signUp(
        email: String,
        password: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        val mFirebaseAuth = Firebase.auth
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(completeListener)
    }

    fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    fun logOut(authStateListener: AuthStateListener) {
        val mFirebaseAuth = Firebase.auth
        mFirebaseAuth.addAuthStateListener(authStateListener)
        mFirebaseAuth.signOut()

    }

    fun saveUserInfoDataToRealtimeDatabase(
        document: String,
        data: Any,
        completeListener: OnCompleteListener<Void?>
    ) {
        database.child(Constants.USER_COLLECTION_REALTIME_DB).child(document).setValue(data)
            .addOnCompleteListener(completeListener)
    }

    fun uploadImage(
        mImageUri: Uri,
        completeListener: OnCompleteListener<Void>
    ) {
        val task = storage.child(Constants.IMAGE_PATH).putFile(mImageUri)
        Tasks.whenAll(task).addOnCompleteListener(completeListener)
    }

    fun loadAndObserveUserInfo(listener: ValueEventListener) {
        database.addValueEventListener(listener)
    }

    fun getUserImage(
        successListener: OnSuccessListener<ByteArray>,
        failureListener: OnFailureListener
    ) {
        val reference = storage.child(Constants.IMAGE_PATH)
        val size: Long = 1024 * 1024 * 5
        reference.getBytes(size).addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
    }

}