package com.b18dccn562.finalproject.base

import android.content.Intent

interface ActivityCallback {
    fun requestPermission()
    fun startActivityFromFragment(intent: Intent)
}