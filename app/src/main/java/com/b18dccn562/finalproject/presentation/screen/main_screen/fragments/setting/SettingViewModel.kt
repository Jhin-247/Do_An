package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.domain.model.User
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _userImage = MutableLiveData<Resource<Bitmap?>>()
    val userImage: LiveData<Resource<Bitmap?>> = _userImage

    private val loadImageSuccessListener = OnSuccessListener<ByteArray> {
        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        _userImage.value = Resource.Success(bitmap)
    }

    private val loadImageFailureListener = OnFailureListener {
        _userImage.value = Resource.Error(exception = it)
    }

    private val userInformationListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            viewModelScope.launch {
                firebaseRepository.getUserImage(loadImageSuccessListener, loadImageFailureListener)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    }

    fun signOut(authStateListener: AuthStateListener) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRepository.logOut(authStateListener)
        }
    }

    fun loadAndObserveUser() {
        viewModelScope.launch {
            firebaseRepository.loadAndObserveUserInfo(userInformationListener)
        }
    }


}