package com.b18dccn562.finalproject.utils

import com.b18dccn562.finalproject.common.Constants
import com.b18dccn562.finalproject.domain.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserHelper @Inject constructor(
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun createUser(user: User, completeListener: OnCompleteListener<Void>) {
        val document = Firebase.firestore.collection(Constants.USER_COLLECTION_FIREBASE)
            .document(user.uid)
        GlobalScope.launch(Dispatchers.IO) {
            document.set(user)
                .addOnCompleteListener(completeListener)
        }
    }

}