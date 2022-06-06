package com.example.usersearchapplication.repository

import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.getFailedResponseDataError
import com.example.usersearchapplication.getSearchResponse
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

class SearchRepositoryTest {

    lateinit var SUT: SearchRepository
    lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        networkManager = mockk(relaxUnitFun = true)
        SUT = spyk(SearchRepository(networkManager))
    }

    @Test
    fun `searchQuery method call searchQuery method of network manager`() {
        every {
            runBlocking {
                networkManager.getSearchResults(any(), any(), any(), any(), any())
            }
        } returns Success(getSearchResponse())

        runBlocking {
            SUT.searchQuery("test", "test", "test", 10, 1)
        }

        verify {
            runBlocking {
                networkManager.getSearchResults("test", "test", "test", 10, 1)
            }
        }
    }

    @Test
    fun `success from network manager should call getSuccessFlowResponse of repo`() {
        every {
            runBlocking {
                networkManager.getSearchResults(any(), any(), any(), any(), any())
            }
        } returns Success(getSearchResponse())

        runBlocking {
            SUT.searchQuery("test", "test", "test", 10, 1)
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
                networkManager.getSearchResults(any(), any(), any(), any(), any())
            }
        } returns DataError(getFailedResponseDataError())

        runBlocking {
            SUT.searchQuery("test", "test", "test", 10, 1)
        }

        verify {
            SUT.getFailedFlowResponse(any())
        }

        verify(exactly = 0) {
            SUT.getSuccessFlowResponse(any())
        }
    }
}