package com.b18dccn562.finalproject.domain.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.b18dccn562.finalproject.domain.db.model.AppEntity

@Dao
interface AppLockStatusDAO {

    @Insert
    fun insertApp(appEntity: AppEntity): Long

    @Query("SELECT * FROM app_lock_status")
    fun getAllApp(): LiveData<List<AppEntity>>

    @Delete
    fun delete(appEntity: AppEntity)

    @Update
    fun updateAppLockStatus(appEntity: AppEntity)

}