package com.example.usersearchapplication.manager

import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.data.network.Resource

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

interface NetworkManager {

    suspend fun getSearchResults(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Resource<GitSearchResponse>

    suspend fun getUserDetails(
        userName: String
    ): Resource<UserDetail>

}