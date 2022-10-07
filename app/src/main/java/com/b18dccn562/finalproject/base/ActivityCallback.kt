package com.b18dccn562.finalproject.base

import android.content.Intent

interface ActivityCallback {
    fun requestUsageStatPermission()
    fun startActivityFromFragment(intent: Intent)
}