package com.b18dccn562.finalproject.di

import com.b18dccn562.finalproject.data.remote.login.LoginApi
import com.b18dccn562.finalproject.data.repository.LoginRepositoryIml
import com.b18dccn562.finalproject.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExecutors(): Executor {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryIml(api)
    }

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi{
        return LoginApi()
    }

}