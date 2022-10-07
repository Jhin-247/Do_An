package com.b18dccn562.finalproject.common

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Constants {

    const val REALTIME_DATABASE_URL =
        "https://focus-10f14-default-rtdb.asia-southeast1.firebasedatabase.app/"

    const val USER_COLLECTION_REALTIME_DB = "user_info"

    const val isInitForUnlockApp = "unlock"
    const val patternKey = "pattern"
    const val pattern = "saved_pattern"

    const val ERROR_HAPPENED = 0
    const val NO_IMAGE = 1

    val IMAGE_PATH = "images/${Firebase.auth.uid}"
}