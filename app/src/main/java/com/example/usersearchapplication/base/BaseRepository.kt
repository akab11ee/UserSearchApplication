package com.example.usersearchapplication.base

import com.example.usersearchapplication.manager.INetworkManager

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

open class BaseRepository(private val networkManager: INetworkManager) {

    fun getNetworkManager(): INetworkManager {
        return networkManager
    }
}