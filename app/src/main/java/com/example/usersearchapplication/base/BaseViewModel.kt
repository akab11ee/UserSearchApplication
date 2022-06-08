package com.example.usersearchapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usersearchapplication.data.model.ApiError
import com.example.usersearchapplication.data.network.DataError
import com.example.usersearchapplication.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

open class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val showLoadingDialog = MutableLiveData(false)
    val showErrorDialog = MutableLiveData<DataError<ApiError>>()

    //capture coroutine exception and show it in dialog to user
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        hideLoading()
        showErrorDialog(ApiError())
    }

    /*
     * Use this method to show generic loading dialog when required
     */
    fun showLoading() {
        if (showLoadingDialog.value == false) {
            showLoadingDialog.value = true
        }
    }

    fun hideLoading() {
        if (showLoadingDialog.value == true) {
            showLoadingDialog.value = false
        }
    }

    /*
    * Use this method to show generic error dialog when required
    */

    fun showErrorDialog(error: ApiError) {
        showErrorDialog.value = DataError(error)
    }

    fun getDispatcher(): DispatcherProvider {
        return dispatcherProvider
    }
}