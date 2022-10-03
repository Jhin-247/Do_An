package com.b18dccn562.finalproject.domain.repository

import com.b18dccn562.finalproject.domain.model.AppInfo

interface AppRepository {
    fun loadAppsFromDevices(startQueryTime: Long, endQueryTime: Long): List<AppInfo>
}