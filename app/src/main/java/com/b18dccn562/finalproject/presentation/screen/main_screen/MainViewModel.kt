package com.b18dccn562.finalproject.presentation.screen.main_screen

import androidx.compose.ui.text.toLowerCase
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
    private val _hasPermission = MutableLiveData(false)
    val hasPermission: LiveData<Boolean> = _hasPermission
    private val _allAppList = MutableLiveData<List<AppInfo>>()
    val allAppList: LiveData<List<AppInfo>> = _allAppList

    private val _listAppToShow = MutableLiveData<List<AppInfo>>()
    val listAppToShow: LiveData<List<AppInfo>> = _listAppToShow

    private val _searchAppResult = MutableLiveData<List<AppInfo>>()
    val searchAppResult: LiveData<List<AppInfo>> = _searchAppResult

    private val _isInitializedData = MutableLiveData(InitializeState.NOT_INITIALIZED)
    val isInitializedData: LiveData<InitializeState> = _isInitializedData

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                _isInitializedData.value = InitializeState.IS_INITIALIZING
            }
            val appList = appRepository.loadAppsFromDevices()
            withContext(Dispatchers.Main) {
                _allAppList.value = appList
                _isInitializedData.value = InitializeState.INITIALIZED
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