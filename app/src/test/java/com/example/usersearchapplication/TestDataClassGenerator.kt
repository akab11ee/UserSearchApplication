package com.example.usersearchapplication

import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.model.UserDetail
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.File

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class TestDataClassGenerator {

    private val gson: Gson = GsonBuilder().create()

    //generic function to  generate data classes from json file path
    private inline fun <reified T> buildDataClassFromJson(json: String): T {
        val jsonAdapter: TypeAdapter<T> = gson.getAdapter(T::class.java)
        return jsonAdapter.fromJson(json)!!
    }


    fun getSuccessSearchResponse(): GitSearchResponse {
        val jsonString = getJson("GitSearchResponse.json")
        return buildDataClassFromJson(jsonString)
    }

    fun getSuccessUserDetail(): UserDetail {
        val jsonString = getJson("UserDetailResponse.json")
        return buildDataClassFromJson(jsonString)
    }

    fun getErrorData(): ApiError {
        val jsonString = getJson("ApiError.json")
        return buildDataClassFromJson(jsonString)
    }


    fun getFailedSearchResponse(): Response<GitSearchResponse> {
        val jsonString = getJson("ApiError.json")

        val errorResponseBody = jsonString.toResponseBody("application/json".toMediaTypeOrNull())

        return Response.error(400, errorResponseBody)
    }

    private fun getJson(resourceName: String): String {
        val file = File("src/main/resources/$resourceName")

        return String(file.readBytes())
    }

}