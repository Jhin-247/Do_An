package com.b18dccn562.finalproject.data.repository

import android.app.Application
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.collection.arrayMapOf
import com.b18dccn562.finalproject.domain.model.AppInfo
import com.b18dccn562.finalproject.utils.AppUtils
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepositoryImpl @Inject constructor(
    var application: Application,
    private var executor: Executor,
    private var appUtils: AppUtils
) {

    fun queryApps(
        startQueryTime: Long,
        endQueryTime: Long
    ) {
        executor.execute {
            try {
                val result = mutableListOf<AppInfo>()
                val map = arrayMapOf<String, UsageStats>()

                val usageStatManager =
                    application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

                val usageStats = usageStatManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_BEST,
                    startQueryTime,
                    endQueryTime
                )

                for (usageStat in usageStats) {
                    if (!isAppInfoAvailable(usageStat)) {
                        continue
                    }
                    val mExistingApp = map[usageStat.packageName]
                    if (mExistingApp == null) {
                        map[usageStat.packageName] = usageStat
                    } else {
                        mExistingApp.add(usageStat)
                    }
                }

                usageStats.clear()
                usageStats.addAll(map.values)

                for (usageStat in usageStats) {
                    val mAppInfo = AppInfo(
                        appUtils.getAppName(usageStat.packageName),
                        usageStat.packageName,
                        appUtils.getAppIconByAppName(usageStat.packageName),
                        usageStat.totalTimeInForeground
                    )

                    if (mAppInfo.mUsedDuration != 0L) {
                        result.add(mAppInfo)
                    }
                }

                result.sortWith { o1, o2 ->
                    o2.mUsedDuration.compareTo(o1.mUsedDuration)
                }


            } catch (exception: Exception) {

            }
        }
    }

    private fun isAppInfoAvailable(usageStat: UsageStats): Boolean {
        return try {
            application.packageManager.getApplicationInfo(usageStat.packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

}