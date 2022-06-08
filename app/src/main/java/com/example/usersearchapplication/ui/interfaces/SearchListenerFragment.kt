package com.example.usersearchapplication.ui.interfaces

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

/** Allows to do search from activity's SearchView */
interface SearchListenerFragment {
    fun doSearch(searchQuery: String)
}