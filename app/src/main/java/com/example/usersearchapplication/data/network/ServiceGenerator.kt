package com.example.usersearchapplication.data.network

import com.example.usersearchapplication.BuildConfig
import com.example.usersearchapplication.utils.NetworkConstant.Companion.CONNECT_TIMEOUT
import com.example.usersearchapplication.utils.NetworkConstant.Companion.CONTENT_TYPE
import com.example.usersearchapplication.utils.NetworkConstant.Companion.CONTENT_TYPE_VALUE
import com.example.usersearchapplication.utils.NetworkConstant.Companion.READ_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class ServiceGenerator @Inject constructor() {

    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder().addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder
            .addInterceptor(headerInterceptor)
            .addInterceptor(logger)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient = okHttpBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getGitService(): GitService {
        return retrofit.create(GitService::class.java)
    }
}