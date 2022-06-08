package com.example.usersearchapplication.ui.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usersearchapplication.base.BaseViewModel
import com.example.usersearchapplication.data.model.GitSearchResponse
import com.example.usersearchapplication.data.network.Resource
import com.example.usersearchapplication.repository.SearchRepository
import com.example.usersearchapplication.utils.AppConstant
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
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    appDispatcher: DispatcherProvider
) : BaseViewModel(appDispatcher) {

    private val _response = MutableLiveData<Resource<GitSearchResponse>>()
    val response: LiveData<Resource<GitSearchResponse>> get() = _response

    private var pageNum: Int = 0

    private var pageSize: Int = 10
    private var itemsCount: Int = 0
    private var totalCount: Int = 0

    fun resetData() {
        pageSize = AppConstant.PAGE_SIZE
        pageNum = 1

        totalCount = 0
        itemsCount = 0
    }

    fun getSearchResults(searchQuery: String) {
        showLoading()
        viewModelScope.launch(coroutineExceptionHandler) {
            searchRepository.searchQuery(
                query = searchQuery,
                sort = AppConstant.SORT,
                order = AppConstant.ORDER,
                pageSize,
                pageNum
            ).flowOn(getDispatcher().io())
                .collect {
                    hideLoading()
                    if (it.data != null) {
                        onSuccess(it)
                    } else {
                        showErrorDialog(it.errorDescription)
                    }
                }
        }
    }

    @VisibleForTesting
    internal fun onSuccess(response: Resource<GitSearchResponse>) {
        if (response.data != null) {
            totalCount = response.data!!.totalCount
            itemsCount += response.data!!.items.size
            _response.value = response
        }
    }

    fun onLoadMore(query: String) {
        if (itemsCount != 0 && totalCount != itemsCount) {
            pageNum++
            getSearchResults(query)
        }
    }

    fun isLastPage(): Boolean {
        return (pageNum * pageSize) >= totalCount
    }
}