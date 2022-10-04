package com.b18dccn562.finalproject.presentation.screen.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b18dccn562.finalproject.domain.model.AppInfo
import com.b18dccn562.finalproject.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var appRepository: AppRepository
) : ViewModel() {

    private val _allAppList = MutableLiveData<List<AppInfo>>()
    val allAppList: LiveData<List<AppInfo>> = _allAppList

    private val hasPermission = MutableLiveData(false)
    private val _isInitializedData = MutableLiveData(InitializeState.NOT_INITIALIZED)
    val isInitializedData: LiveData<InitializeState> = _isInitializedData

    fun setHasPermissionStatus(status: Boolean) {
        hasPermission.value = status
    }

    fun getHasPermissionStatus(): LiveData<Boolean> = hasPermission

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                _isInitializedData.value = InitializeState.IS_INITIALIZING
            }
            val appList = appRepository.loadAppsFromDevices()
            withContext(Dispatchers.Main) {
                _allAppList.value = appList
                _isInitializedData.value = InitializeState.INITIALIZED
            }
        }
    }


}