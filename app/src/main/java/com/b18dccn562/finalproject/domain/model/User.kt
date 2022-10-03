package com.b18dccn562.finalproject.domain.model

import com.b18dccn562.finalproject.base.Equatable

data class User(
    val uid: String,
    val name: String,
    val role: String,
    val gender: String,
    val email: String,
    val subEmails: List<String> = listOf()
) : Equatable
