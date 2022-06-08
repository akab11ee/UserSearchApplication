package com.example.usersearchapplication.base

import com.example.usersearchapplication.manager.NetworkManager

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

open class BaseRepository(private val networkManager: NetworkManager) {

    fun getNetworkManager(): NetworkManager {
        return networkManager
    }
}