package com.b18dccn562.finalproject.presentation.screen.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b18dccn562.finalproject.data.repository.AppRepositoryImpl
import com.b18dccn562.finalproject.domain.model.AppInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var appRepositoryImpl: AppRepositoryImpl
) : ViewModel() {

    private val appList = MutableLiveData<List<AppInfo>>()

    private val hasPermission = MutableLiveData(false)
    private val isInitializedData = MutableLiveData(false)

    fun setHasPermissionStatus(status: Boolean) {
        hasPermission.value = status
    }

    fun getHasPermissionStatus(): LiveData<Boolean> = hasPermission
    fun getInitializedState(): Boolean = isInitializedData.value!!
    fun getAppList(): LiveData<List<AppInfo>> = appList

    fun loadData() {

        isInitializedData.value = true
    }


}