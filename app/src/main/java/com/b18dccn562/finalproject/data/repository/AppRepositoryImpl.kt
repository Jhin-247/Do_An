package com.b18dccn562.finalproject.data.repository

import com.b18dccn562.finalproject.domain.model.AppInfo
import com.b18dccn562.finalproject.domain.repository.AppRepository
import com.b18dccn562.finalproject.utils.AppUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val appUtils: AppUtils
) : AppRepository {

    override fun loadAppsFromDevices(): List<AppInfo> {
        return appUtils.loadAllApp()
    }

}