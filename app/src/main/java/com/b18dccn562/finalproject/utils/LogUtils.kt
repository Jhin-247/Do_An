package com.b18dccn562.finalproject.utils

import android.util.Log
import com.b18dccn562.finalproject.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton
object LogUtils{

    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d("LogUtils", message)
        }
    }
}