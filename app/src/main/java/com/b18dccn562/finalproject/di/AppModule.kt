package com.b18dccn562.finalproject.di

import com.b18dccn562.finalproject.data.remote.login.LoginApi
import com.b18dccn562.finalproject.data.repository.AppRepositoryImpl
import com.b18dccn562.finalproject.data.repository.LoginRepositoryImpl
import com.b18dccn562.finalproject.domain.repository.AppRepository
import com.b18dccn562.finalproject.domain.repository.LoginRepository
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
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return LoginApi()
    }

    @Provides
    @Singleton
    fun provideAppRepository(appUtils: AppUtils): AppRepository {
        return AppRepositoryImpl(appUtils)
    }

}