package com.example.usersearchapplication.repository

import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.getFailedResponseDataError
import com.example.usersearchapplication.getUserDetailResponse
import com.example.usersearchapplication.manager.NetworkManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class UserDetailRepositoryTest {

    lateinit var SUT: UserDetailRepository
    lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        networkManager = mockk(relaxUnitFun = true)
        SUT = spyk(UserDetailRepository(networkManager))
    }

    @Test
    fun `getUserDetails method call getUserDetails method of network manager`() {
        every {
            runBlocking {
                networkManager.getUserDetails(any())
            }
        } returns Success(getUserDetailResponse())

        runBlocking {
            SUT.getUserDetails("test")
        }

        verify {
            runBlocking {
                networkManager.getUserDetails("test")
            }
        }
    }

    @Test
    fun `success from network manager should call getSuccessFlowResponse of repo`() {
        every {
            runBlocking {
                networkManager.getUserDetails(any())
            }
        } returns Success(getUserDetailResponse())

        runBlocking {
            SUT.getUserDetails("test")
        }

        verify {
            SUT.getSuccessFlowResponse(any())
        }

        verify(exactly = 0) {
            SUT.getFailedFlowResponse(any())
        }
    }

    @Test
    fun `failure from network manager should call getFailedFlowResponse of repo`() {
        every {
            runBlocking {
                networkManager.getUserDetails(any())
            }
        } returns DataError(getFailedResponseDataError())

        runBlocking {
            SUT.getUserDetails("test")
        }

        verify {
            SUT.getFailedFlowResponse(any())
        }

        verify(exactly = 0) {
            SUT.getSuccessFlowResponse(any())
        }
    }
}