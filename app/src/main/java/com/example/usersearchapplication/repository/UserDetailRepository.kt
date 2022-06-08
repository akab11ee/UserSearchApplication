package com.example.usersearchapplication.repository

import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.data.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

interface UserDetailRepository {
    suspend fun getUserDetails(
        userName: String
    ): Flow<Resource<UserDetail>>
}