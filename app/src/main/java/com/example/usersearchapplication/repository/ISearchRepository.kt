package com.example.usersearchapplication.repository

import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

interface ISearchRepository {
    suspend fun searchQuery(
        query: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Flow<Resource<GitSearchResponse>>
}