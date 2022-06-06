package com.example.usersearchapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

data class GitSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<SearchItem>
)