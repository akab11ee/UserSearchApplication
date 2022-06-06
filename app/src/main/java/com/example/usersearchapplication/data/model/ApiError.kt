package com.example.usersearchapplication.data.model

import com.example.usersearchapplication.utils.NetworkConstant.Companion.DOCUMENTATION_URL
import com.example.usersearchapplication.utils.NetworkConstant.Companion.SOMETHING_WENT_WRONG
import com.google.gson.annotations.SerializedName

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

data class ApiError(
    @SerializedName("message") val message: String = SOMETHING_WENT_WRONG,
    @SerializedName("documentation_url") val documentationUrl: String = DOCUMENTATION_URL
)
