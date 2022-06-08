package com.example.usersearchapplication.ui.interfaces

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

/** Allows to manipulate activity's SearchView from child fragments in nav_graph.xml */
interface SearchListenerActivity {
    // every child fragment should have this in onResume() with argument "false" except SearchFragment
    fun showSearchView(isShown: Boolean)

    // to set initial value into SearchView coz 1st request is made from another SearchView in HomeFragment
    fun setSearchText(searchQuery: String)

    // callback to do a real search in SearchFragment
    fun registerSearchFragment(instance: SearchListenerFragment)
}