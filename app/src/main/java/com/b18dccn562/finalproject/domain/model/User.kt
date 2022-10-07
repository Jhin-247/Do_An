package com.b18dccn562.finalproject.domain.model

import com.b18dccn562.finalproject.base.Equatable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = null,
    var name: String? = null,
    var role: String? = null,
    var gender: String? = null,
    var email: String? = null,
    var avatarUrl: String? = null
) : Equatable
