package com.example.usersearchapplication.manager

import androidx.annotation.VisibleForTesting
import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.data.network.Resource
import com.example.usersearchapplication.data.network.ServiceGenerator
import com.example.usersearchapplication.data.network.Success
import com.example.usersearchapplication.utils.NetworkConstant.Companion.SOMETHING_WENT_WRONG
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class NetworkManagerImpl @Inject constructor(private val serviceGenerator: ServiceGenerator) :
    NetworkManager {
    override suspend fun getSearchResults(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Resource<GitSearchResponse> {
        val gitService = serviceGenerator.getGitService()

        return when (val responseBodyPojo =
            processCall { gitService.getSearchResult(query, sort, order, perPage, page) }
        ) {
            is GitSearchResponse -> Success(responseBodyPojo)
            else -> DataError(responseBodyPojo as ApiError)
        }
    }

    override suspend fun getUserDetails(userName: String): Resource<UserDetail> {
        val gitService = serviceGenerator.getGitService()

        return when (val responseBodyPojo =
            processCall { gitService.getUserDetail(userName) }
        ) {
            is UserDetail -> Success(responseBodyPojo)
            else -> DataError(responseBodyPojo as ApiError)
        }
    }

    private inline fun processCall(request: () -> Response<*>): Any? {
        val response = request.invoke()

        return try {

            if (response.isSuccessful) {
                return response.body()
            } else {
                return getErrorBody(response)
            }
        } catch (e: IOException) {
            return ApiError()
        }

    }

    @VisibleForTesting
    internal fun getErrorBody(response: Response<*>): ApiError {
        val gson = GsonBuilder().create()
        var apiError = ApiError()
        try {
            if (response.errorBody() != null) {
                apiError = gson.fromJson(response.errorBody()!!.string(), ApiError::class.java)
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            apiError = ApiError(message = e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            apiError = ApiError(message = e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: IOException) {
            e.printStackTrace()
            apiError = ApiError(message = e.message ?: SOMETHING_WENT_WRONG)
        }

        return apiError
    }

}