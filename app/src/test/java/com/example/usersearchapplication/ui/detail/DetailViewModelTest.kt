package com.example.usersearchapplication.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.usersearchapplication.TestDispatcherProvider
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.getUserDetailResponse
import com.example.usersearchapplication.repository.UserDetailRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

class DetailViewModelTest {

    lateinit var SUT: DetailViewModel
    lateinit var userDetailRepository: UserDetailRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val appDispatcher = TestDispatcherProvider()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        userDetailRepository = mockk(relaxUnitFun = true)
        SUT = spyk(DetailViewModel(userDetailRepository, appDispatcher))
    }

    @Test
    fun `getUserDetail method should call user detail query of repository`() {

        every {
            runBlocking {
                userDetailRepository.getUserDetails(any())
            }
        } returns flow {
            emit(Success(getUserDetailResponse()))
        }

        SUT.getUserDetail("test")

        verify {
            runBlocking {
                userDetailRepository.getUserDetails("test")
            }
        }
    }
}