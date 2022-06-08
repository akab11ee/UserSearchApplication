package com.example.usersearchapplication.manager


import com.example.usersearchapplication.data.network.ServiceGenerator
import com.example.usersearchapplication.getFailedResponse
import com.example.usersearchapplication.getSearchResponse
import com.example.usersearchapplication.getUserDetailResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class NetworkManagerTest {

    lateinit var SUT: NetworkManagerImpl
    lateinit var serviceGenerator: ServiceGenerator

    @Before
    fun setUp() {
        serviceGenerator = mockk(relaxUnitFun = true)
        SUT = spyk(NetworkManagerImpl(serviceGenerator))
    }

    @Test
    fun `network manager getSearchResults should call git api service getSearchResults method`() {
        every {
            runBlocking {
                serviceGenerator.getGitService().getSearchResult(any(), any(), any(), any(), any())
            }
        } returns Response.success(getSearchResponse())

        runBlocking {
            SUT.getSearchResults("test", "test", "test", 10, 1)
        }

        verify {
            runBlocking {
                serviceGenerator.getGitService().getSearchResult("test", "test", "test", 10, 1)
            }
        }
    }

    @Test
    fun `network manager getUserDetails should call git api service getUserDetails method`() {
        every {
            runBlocking {
                serviceGenerator.getGitService().getUserDetail(any())
            }
        } returns Response.success(getUserDetailResponse())

        runBlocking {
            SUT.getUserDetails("test")
        }

        verify {
            runBlocking {
                serviceGenerator.getGitService().getUserDetail("test")
            }
        }
    }

    @Test
    fun `failed response should call getErrorBody method`() {
        every {
            runBlocking {
                serviceGenerator.getGitService().getSearchResult(any(), any(), any(), any(), any())
            }
        } returns getFailedResponse()

        runBlocking {
            SUT.getSearchResults("test", "test", "test", 10, 1)
        }

        verify {
            runBlocking {
                SUT.getErrorBody(any())
            }
        }
    }
}