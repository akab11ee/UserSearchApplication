package com.example.usersearchapplication.data.network

import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.utils.AppConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

interface GitService {

    @GET(AppConstant.SEARCH_API)
    suspend fun getSearchResult(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<GitSearchResponse>

    @GET(AppConstant.USER_DETAIL_API)
    suspend fun getUserDetail(
        @Path("USER_NAME") username: String
    ): Response<UserDetail>

}