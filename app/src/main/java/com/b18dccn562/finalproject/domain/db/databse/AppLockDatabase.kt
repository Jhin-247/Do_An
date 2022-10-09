package com.b18dccn562.finalproject.domain.db.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.b18dccn562.finalproject.domain.db.dao.AppLockStatusDAO
import com.b18dccn562.finalproject.domain.db.model.AppEntity

@Database(entities = [AppEntity::class], version = 1)
abstract class AppLockDatabase : RoomDatabase() {

    abstract fun appLockDao(): AppLockStatusDAO

    companion object {
        @Volatile
        private var INSTANCE: AppLockDatabase? = null
        fun getDatabase(context: Context): AppLockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppLockDatabase::class.java,
                    "app_lock_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}