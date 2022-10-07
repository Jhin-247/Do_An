package com.b18dccn562.finalproject.presentation.screen.sign_up_info_screen

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b18dccn562.finalproject.common.Constants
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.domain.model.User
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.b18dccn562.finalproject.utils.LogUtils
import com.google.android.gms.tasks.OnCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpInfoViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _saveInformationStatus = MutableLiveData<Resource<Void?>>()
    val saveInformationStatus: LiveData<Resource<Void?>> = _saveInformationStatus

    private val _uploadImageState = MutableLiveData<Resource<Void?>>()
    val uploadImageState: LiveData<Resource<Void?>> = _uploadImageState

    private var mImageUri: Uri? = null

    private val completeUploadUserInformationListener = OnCompleteListener<Void?> {
        LogUtils.d("${it.isComplete}")
        if (it.isSuccessful) {
            _saveInformationStatus.value = Resource.Success(null)
        } else {
            _saveInformationStatus.value =
                Resource.Error(message = it.exception?.message, exception = it.exception)
        }
    }

    private val completeUploadListener = OnCompleteListener {
        if (it.isSuccessful) {
            _uploadImageState.postValue(Resource.Success(it.result))
        } else {
            _uploadImageState.value = Resource.Error(
                message = it.exception?.message,
                exception = it.exception,
                errorCode = Constants.ERROR_HAPPENED
            )
        }
    }

    fun registerInformation(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _saveInformationStatus.value = Resource.Loading()
            }
            firebaseRepository.saveDataToRealtimeDatabase(
                firebaseRepository.getUser()!!.uid,
                user,
                completeUploadUserInformationListener
            )
        }
    }

    fun setImage(imageUri: Uri) {
        this.mImageUri = imageUri
    }

    fun uploadImage() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _uploadImageState.value = Resource.Loading()
            }
            val imageToSave = mImageUri
            if (imageToSave == null) {
                withContext(Dispatchers.Main) {
                    _uploadImageState.value = Resource.Error(errorCode = Constants.NO_IMAGE)
                }
            } else {
                firebaseRepository.uploadImage(
                    imageToSave,
                    completeUploadListener
                )
            }
        }
    }
}