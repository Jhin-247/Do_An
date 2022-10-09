package com.b18dccn562.finalproject.presentation.screen.main_screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.domain.model.AppInfo
import com.b18dccn562.finalproject.domain.repository.AppRepository
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var appRepository: AppRepository,
    private var firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _hasPermission = MutableLiveData(false)
    val hasPermission: LiveData<Boolean> = _hasPermission
    private val _allAppList = MutableLiveData<List<AppInfo>>()

    private val _listAppToShow = MutableLiveData<List<AppInfo>>()
    val listAppToShow: LiveData<List<AppInfo>> = _listAppToShow

    private val _searchAppResult = MutableLiveData<List<AppInfo>>()

    private val _isInitializedData = MutableLiveData<Resource<Void?>>()
    val isInitializedData: LiveData<Resource<Void?>> = _isInitializedData

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

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                _isInitializedData.value = Resource.Loading()
            }
            val appList = appRepository.loadAppsFromDevices()
            withContext(Dispatchers.Main) {
                _allAppList.value = appList
                _isInitializedData.value = Resource.Success(null)
                _listAppToShow.value = _allAppList.value
            }
        }
    }

    fun setHasPermissionStatus(status: Boolean) {
        _hasPermission.value = status
    }

    fun search(keySearch: String) {
        val appList = _allAppList.value
        val searchList = mutableListOf<AppInfo>()

        appList?.let {
            for (appInfo in it) {
                if (appInfo.mAppName.lowercase().contains(keySearch.lowercase())) {
                    searchList.add(appInfo)
                }
            }
        }

        _searchAppResult.value = searchList
        _listAppToShow.value = _searchAppResult.value
    }

    fun stopSearch() {
        _listAppToShow.value = _allAppList.value
    }


}