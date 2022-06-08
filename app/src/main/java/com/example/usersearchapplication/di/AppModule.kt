package com.example.usersearchapplication.di

import android.content.Context
import com.example.usersearchapplication.data.network.ServiceGenerator
import com.example.usersearchapplication.manager.NetworkManager
import com.example.usersearchapplication.manager.NetworkManagerImpl
import com.example.usersearchapplication.repository.SearchRepository
import com.example.usersearchapplication.repository.SearchRepositoryImpl
import com.example.usersearchapplication.repository.UserDetailRepository
import com.example.usersearchapplication.repository.UserDetailRepositoryImpl
import com.example.usersearchapplication.utils.coroutines.AppDispatcher
import com.example.usersearchapplication.utils.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideServiceGenerator(): ServiceGenerator = ServiceGenerator()

    @Provides
    @Singleton
    fun provideNetworkManager(networkManager: NetworkManagerImpl): NetworkManager {
        return networkManager
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(appDispatcher: AppDispatcher): DispatcherProvider {
        return appDispatcher
    }

    @Provides
    @Singleton
    fun provideUserDetailRepo(networkManager: NetworkManager): UserDetailRepository {
        return UserDetailRepositoryImpl(networkManager)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(networkManager: NetworkManager): SearchRepository {
        return SearchRepositoryImpl(networkManager)
    }
}