package com.example.usersearchapplication.ui

import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.usersearchapplication.R
import com.example.usersearchapplication.base.BaseActivity
import com.example.usersearchapplication.databinding.ActivityHomeBinding
import com.example.usersearchapplication.ui.interfaces.SearchListenerActivity
import com.example.usersearchapplication.ui.interfaces.SearchListenerFragment
import com.example.usersearchapplication.utils.NetworkUtils
import com.example.usersearchapplication.utils.hideKeyboard
import com.example.usersearchapplication.utils.toastL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), SearchListenerActivity {

    override val viewModel: HomeViewModel by viewModels()
    lateinit var navController: NavController
    private var searchFragment: SearchListenerFragment? = null

    override fun setListener() {
        super.setListener()
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        initSearchListener()
    }

    private fun initSearchListener() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    if (NetworkUtils.getNetwork(applicationContext)) {
                        searchFragment?.doSearch(query.trim())
                        hideKeyboard()
                    } else
                        this@HomeActivity.toastL(resources.getString(R.string.message_offline))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun showSearchView(isShown: Boolean) {
        search_view.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    override fun setSearchText(searchQuery: String) {
        search_view.setQuery(searchQuery, false)
    }

    override fun registerSearchFragment(instance: SearchListenerFragment) {
        searchFragment = instance
    }

    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)


}