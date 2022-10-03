package com.b18dccn562.finalproject.presentation.screen.login_screen.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.domain.repository.LoginRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<AuthResult>>()
    val loginState: LiveData<Resource<AuthResult>> = _loginState

    private val loginCompleteListener = OnCompleteListener {
        if (it.isSuccessful) {
            CoroutineScope(Dispatchers.IO).launch {
                _loginState.postValue(Resource.Success(it.result))
            }
        } else {
            try {
                throw it.exception!!
            } catch (exception: Exception) {
                handelFirebaseException(exception)
            }
        }
    }

    private fun handelFirebaseException(exception: Exception) {
        _loginState.value = Resource.Error(exception.toString(), null, exception)
    }

    fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loginRepository.signUp(email, password, loginCompleteListener)
            } catch (exception: Exception) {
                handelFirebaseException(exception)
            }
        }
    }

    fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            loginRepository.signIn(email, password, loginCompleteListener)
        }
    }

}