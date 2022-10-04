package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting

import androidx.lifecycle.ViewModel
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    fun signOut(authStateListener: AuthStateListener) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRepository.logOut(authStateListener)
        }
    }


}