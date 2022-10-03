package com.b18dccn562.finalproject.utils

import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.ArrayMap
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.domain.model.AppInfo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppUtils @Inject constructor(
    private var application: Application
) {

    fun getAppIconByAppName(packageName: String): Drawable? {
        try {
            val packageManager: PackageManager = application.packageManager
            return packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return AppCompatResources.getDrawable(
            application,
            R.drawable.ic_app_launcher
        )
    }

    fun getAppName(packageName: String): String {
        val packageManager: PackageManager = application.packageManager
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return (
                (if (applicationInfo != null)
                    packageManager.getApplicationLabel(applicationInfo) else "Unknown") as String
                )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getAllApp() {
        var mMaxUseDuration = 0L
        val mAppInfoList: MutableList<AppInfo> = ArrayList()
        val map: ArrayMap<String, UsageStats> = ArrayMap()

        val mCurrentTime = System.currentTimeMillis()
        val mStartTime = mCurrentTime - 24 * 60 * 60 * 1000
        val mUsageStats =
            (application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager)
                .queryUsageStats(UsageStatsManager.INTERVAL_BEST, mStartTime, mCurrentTime)
        mUsageStats.forEach { usageStats ->
            //            if (!isAppInfoAvailable(usageStats)) {
            //                continue
            //            }
            val mExistingStat: UsageStats = map.get(usageStats.packageName)!!
            //            if (mExistingStat == null) {
            //                map.put(usageStats.packageName, usageStats)
            //            } else {
            mExistingStat.add(usageStats)
            //            }
        }

        mUsageStats.clear()
        mUsageStats.addAll(map.values)

        for (usageStats in mUsageStats) {
            val mAppInfo = AppInfo()
            mAppInfo.mPackageName = usageStats.packageName
            mAppInfo.mAppName = (getAppName(usageStats.packageName))
            mAppInfo.mAppIcon = getAppIconByAppName(mAppInfo.mPackageName)
            mAppInfo.mUsedDuration = usageStats.totalTimeInForeground
            if (usageStats.totalTimeInForeground > mMaxUseDuration) {
                mMaxUseDuration = usageStats.totalTimeInForeground
            }
            if (mAppInfo.mUsedDuration != 0L) {
                mAppInfoList.add(mAppInfo)
            }
        }
    }

}