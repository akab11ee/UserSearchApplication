package com.example.usersearchapplication.data.network

import com.example.usersearchapplication.data.model.ApiError

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

sealed class Resource<T>(
    var data: T?,
    var errorDescription: ApiError = ApiError()
)

class Success<T>(data: T) : Resource<T>(data = data)
class DataError<T>(apiError: ApiError) : Resource<T>(null, apiError)