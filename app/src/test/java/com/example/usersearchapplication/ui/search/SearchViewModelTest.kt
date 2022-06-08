package com.example.usersearchapplication.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.usersearchapplication.TestDispatcherProvider
import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.getFailedResponseDataError
import com.example.usersearchapplication.getSearchResponse
import com.example.usersearchapplication.repository.SearchRepository
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

class SearchViewModelTest {

    lateinit var SUT: SearchViewModel
    lateinit var repo: SearchRepository

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
        repo = mockk(relaxUnitFun = true)
        SUT = spyk(SearchViewModel(repo, appDispatcher))
    }

    @Test
    fun `getSearchResults method should call search query of repository`() {

        every {
            runBlocking {
                repo.searchQuery(any(), any(), any(), any(), any())
            }
        } returns flow {
            emit(Success(getSearchResponse()))
        }
        SUT.getSearchResults("test")

        verify {
            runBlocking {
                repo.searchQuery("test", any(), any(), any(), any())
            }
        }
    }

    @Test
    fun `getSearchResults success response should call onSuccess of SUT`() {

        every {
            runBlocking {
                repo.searchQuery(any(), any(), any(), any(), any())
            }
        } returns flow {
            emit(Success(getSearchResponse()))
        }

        SUT.getSearchResults("test")

        verify {
            SUT.onSuccess(any())
        }
    }

    @Test
    fun `getSearchResults error response should not call onSuccess of SUT`() {

        every {
            runBlocking {
                repo.searchQuery(any(), any(), any(), any(), any())
            }
        } returns flow {
            emit(DataError(getFailedResponseDataError()))
        }

        SUT.getSearchResults("test")

        verify(exactly = 0) {
            SUT.onSuccess(any())
        }
    }

}