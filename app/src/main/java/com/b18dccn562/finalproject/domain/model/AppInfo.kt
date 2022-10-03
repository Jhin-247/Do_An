package com.b18dccn562.finalproject.domain.model

import android.graphics.drawable.Drawable
import com.b18dccn562.finalproject.base.Equatable

data class AppInfo(
    var mAppName: String = "",
    var mPackageName: String = "",
    var mAppIcon: Drawable? = null,
    var mUsedDuration: Long = 0
) : Equatable