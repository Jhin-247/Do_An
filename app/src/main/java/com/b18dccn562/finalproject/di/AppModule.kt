package com.b18dccn562.finalproject.di

import com.b18dccn562.finalproject.data.remote.firebase.FirebaseApi
import com.b18dccn562.finalproject.data.repository.AppRepositoryImpl
import com.b18dccn562.finalproject.data.repository.FirebaseRepositoryImpl
import com.b18dccn562.finalproject.domain.repository.AppRepository
import com.b18dccn562.finalproject.domain.repository.FirebaseRepository
import com.b18dccn562.finalproject.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(api: FirebaseApi): FirebaseRepository {
        return FirebaseRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginApi(): FirebaseApi {
        return FirebaseApi()
    }

    @Provides
    @Singleton
    fun provideAppRepository(appUtils: AppUtils): AppRepository {
        return AppRepositoryImpl(appUtils)
    }

}