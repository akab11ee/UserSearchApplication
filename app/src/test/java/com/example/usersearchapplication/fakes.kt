package com.example.usersearchapplication

import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.model.UserDetail
import retrofit2.Response

val testDataClassGenerator = TestDataClassGenerator()

fun getSearchResponse(): GitSearchResponse {
    return testDataClassGenerator.getSuccessSearchResponse()
}

fun getFailedResponse(): Response<GitSearchResponse> {
    return testDataClassGenerator.getFailedSearchResponse()
}

fun getFailedResponseDataError(): ApiError {
    return testDataClassGenerator.getErrorData()
}

fun getUserDetailResponse(): UserDetail {
    return testDataClassGenerator.getSuccessUserDetail()
}


