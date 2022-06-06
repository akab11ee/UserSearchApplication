package com.example.usersearchapplication.repository

import androidx.annotation.VisibleForTesting
import com.example.usersearchapplication.base.BaseRepository
import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Resource
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.manager.INetworkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class UserDetailRepository @Inject constructor(networkManager: INetworkManager) :
    BaseRepository(networkManager), IUserDetailRepository {

    override suspend fun getUserDetails(
        userName: String
    ): Flow<Resource<UserDetail>> {
        val response = getNetworkManager().getUserDetails(userName)

        if (response.data != null) {
            return getSuccessFlowResponse(response.data!!)
        } else {
            return getFailedFlowResponse(response.errorDescription)
        }
    }

    @VisibleForTesting
    internal fun getSuccessFlowResponse(response: UserDetail): Flow<Resource<UserDetail>> {
        return flow {
            emit(Success(response))
        }
    }

    @VisibleForTesting
    internal fun getFailedFlowResponse(response: ApiError): Flow<Resource<UserDetail>> {
        return flow {
            emit(DataError(response))
        }
    }

}