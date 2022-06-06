package com.example.usersearchapplication

import com.example.usersearchapplication.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcher = TestCoroutineDispatcher()

    override fun comp(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun io(): CoroutineDispatcher {

        return testDispatcher
    }


    override fun main(): CoroutineDispatcher {
        return testDispatcher
    }
}