package com.b18dccn562.finalproject.utils

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyBoardUtils @Inject constructor(
    private var application: Application
){

    fun hideKeyBoard(view: View) {
        (application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(
                view.windowToken,
                0
            )
    }

}