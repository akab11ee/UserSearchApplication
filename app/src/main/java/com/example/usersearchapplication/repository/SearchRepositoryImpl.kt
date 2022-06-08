package com.example.usersearchapplication.repository

import androidx.annotation.VisibleForTesting
import com.example.usersearchapplication.base.BaseRepository
import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Resource
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.manager.NetworkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class SearchRepositoryImpl @Inject constructor(networkManager: NetworkManager) :
    BaseRepository(networkManager), SearchRepository {

    override suspend fun searchQuery(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Flow<Resource<GitSearchResponse>> {
        val response = getNetworkManager().getSearchResults(query, sort, order, perPage, page)

        if (response.data != null) {
            return getSuccessFlowResponse(response.data!!)
        } else {
            return getFailedFlowResponse(response.errorDescription)
        }
    }

    @VisibleForTesting
    internal fun getSuccessFlowResponse(response: GitSearchResponse): Flow<Resource<GitSearchResponse>> {
        return flow {
            emit(Success(response))
        }
    }

    @VisibleForTesting
    internal fun getFailedFlowResponse(response: ApiError): Flow<Resource<GitSearchResponse>> {
        return flow {
            emit(DataError(response))
        }
    }

}