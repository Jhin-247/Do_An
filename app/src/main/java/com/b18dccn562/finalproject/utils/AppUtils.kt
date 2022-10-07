package com.b18dccn562.finalproject.utils

import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.util.ArrayMap
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.domain.model.AppInfo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppUtils @Inject constructor(
    private val application: Application
) {

    private var packageManager: PackageManager = application.packageManager

    private fun getAppIconByAppName(packageName: String): Drawable? {
        try {
            return packageManager.getApplicationIcon(packageName)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        return AppCompatResources.getDrawable(
            application,
            R.drawable.ic_app_launcher
        )
    }

    private fun getAppName(packageName: String): String {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        return (
                (if (applicationInfo != null)
                    packageManager.getApplicationLabel(applicationInfo) else "Unknown") as String
                )
    }

    private fun isSystemApp(packageName: String): Boolean {
        val applicationInfo: ApplicationInfo?
        return try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getLaunchIntentForPackage(applicationInfo.packageName) == null
        } catch (exception: Exception) {
            false
        }
    }

    fun loadAppsFromDevices(): List<AppInfo> {
        val mAppInfoList: MutableList<AppInfo> = ArrayList()
        val map: ArrayMap<String, UsageStats> = ArrayMap()

        val mCurrentTime = System.currentTimeMillis()
        val mUsageStats =
            (application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager)
                .queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, mCurrentTime * 2)
        mUsageStats.forEach { usageStats ->
            if (isAppInfoAvailable(usageStats)) {
                val mExistingStat: UsageStats? = map[usageStats.packageName]
                map[usageStats.packageName] = usageStats
                mExistingStat?.add(usageStats)
            }
        }

        mUsageStats.clear()
        mUsageStats.addAll(map.values)

        for (usageStats in mUsageStats) {
            if (isSystemApp(usageStats.packageName)) {
                continue
            }
            val mAppInfo = AppInfo()
            mAppInfo.mPackageName = usageStats.packageName
            mAppInfo.mAppName = getAppName(usageStats.packageName)
            mAppInfo.mAppIcon = getAppIconByAppName(mAppInfo.mPackageName)
            mAppInfo.mUsedDuration = usageStats.totalTimeInForeground
            if (mAppInfo.mPackageName != mAppInfo.mAppName)
                mAppInfoList.add(mAppInfo)
        }
        mAppInfoList.sortWith { o1: AppInfo, o2: AppInfo ->
            o1.mAppName.compareTo(o2.mAppName)
        }
        LogUtils.d("UsageStat size: ${mAppInfoList.size}")
        return mAppInfoList
    }

    private fun isAppInfoAvailable(usageStats: UsageStats): Boolean {
        return try {
            packageManager.getApplicationInfo(usageStats.packageName, 0)
            true
        } catch (e: NameNotFoundException) {
            false
        }
    }

    fun loadAllAppFromDevice(): List<AppInfo> {
        val applicationInfoList = mutableListOf<ApplicationInfo>()
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (packageInfo in packages) {
            if (packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null) {
                applicationInfoList.add(packageInfo)
            }
        }
        LogUtils.d("Application info size: ${applicationInfoList.size}")

        val result: MutableList<AppInfo> = ArrayList()
        for (applicationInfo in applicationInfoList) {
            result.add(convertApplicationInfo(applicationInfo))
        }

        result.sortWith { o1: AppInfo, o2: AppInfo ->
            o1.mAppName.compareTo(o2.mAppName)
        }

        return result
    }

    private fun convertApplicationInfo(applicationInfo: ApplicationInfo): AppInfo {
//        LogUtils.d(getAppName(applicationInfo.packageName))
        return AppInfo(
            getAppName(applicationInfo.packageName),
            applicationInfo.packageName,
            getAppIconByAppName(applicationInfo.packageName),
            0L
        )
    }

    fun loadAllApp(): List<AppInfo> {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val lst: List<ResolveInfo> = packageManager.queryIntentActivities(i, 0)
        val result: MutableList<AppInfo> = ArrayList()
        for (resolveInfo in lst) {
            result.add(convertResolveInfo(resolveInfo))
        }

        result.sortWith { o1: AppInfo, o2: AppInfo ->
            o1.mAppName.compareTo(o2.mAppName)
        }

        return result

    }

    private fun convertResolveInfo(resolveInfo: ResolveInfo): AppInfo {
        return AppInfo(
            getAppName(resolveInfo.activityInfo.packageName),
            resolveInfo.activityInfo.packageName,
            getAppIconByAppName(resolveInfo.activityInfo.packageName),
            0L
        )
    }

}