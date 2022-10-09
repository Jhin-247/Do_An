package com.b18dccn562.finalproject.domain.db.model

import android.graphics.drawable.Drawable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.b18dccn562.finalproject.base.Equatable

@Entity(tableName = "app_lock_status")
data class AppEntity(
    var mAppName: String = "",
    @PrimaryKey
    @NonNull
    var mPackageName: String = "",
    var mAppIcon: Drawable? = null,
    var mUsedDuration: Long = 0,
    var isLocked: Boolean? = false,
    var isLimited: Long? = 0
) : Equatable
