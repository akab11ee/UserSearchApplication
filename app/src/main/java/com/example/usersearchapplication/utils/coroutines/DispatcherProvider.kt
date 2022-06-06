package com.example.usersearchapplication.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

interface DispatcherProvider {
    fun comp(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun main(): CoroutineDispatcher = Dispatchers.Main
}