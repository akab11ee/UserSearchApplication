package com.example.usersearchapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usersearchapplication.base.BaseViewModel
import com.example.usersearchapplication.data.model.UserDetail
import com.example.usersearchapplication.data.network.Resource
import com.example.usersearchapplication.repository.UserDetailRepository
import com.example.usersearchapplication.repository.UserDetailRepositoryImpl
import com.example.usersearchapplication.utils.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: UserDetailRepository,
    appDispatcher: DispatcherProvider
) : BaseViewModel(appDispatcher) {

    private val _response = MutableLiveData<Resource<UserDetail>>()
    val response: LiveData<Resource<UserDetail>> get() = _response

    fun getUserDetail(userName: String) {
        showLoading()
        viewModelScope.launch(coroutineExceptionHandler) {
            detailRepository.getUserDetails(
                userName = userName,
            ).flowOn(getDispatcher().io())
                .collect {
                    hideLoading()
                    if (it.data != null) {
                        _response.value = it
                    } else {
                        showErrorDialog(it.errorDescription)
                    }
                }
        }
    }
}