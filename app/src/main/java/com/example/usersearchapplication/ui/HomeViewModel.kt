package com.example.usersearchapplication.ui

import com.example.usersearchapplication.base.BaseViewModel
import com.example.usersearchapplication.utils.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    appDispatcher: DispatcherProvider
) : BaseViewModel(appDispatcher) {
}