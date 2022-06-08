package com.example.usersearchapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.usersearchapplication.R
import com.example.usersearchapplication.ui.interfaces.SearchListenerActivity
import com.example.usersearchapplication.utils.NetworkUtils
import com.example.usersearchapplication.utils.hideKeyboard
import com.example.usersearchapplication.utils.navigate
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchListener()
        initNetworkChangesListener()
    }

    /** SearchView btn action */
    private fun initSearchListener() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    val action = HomeFragmentDirections.actionSearch(query.trim())
                    navigate(action)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    /**
     * If network connection is missing UI will suggest to connect to internet.
     * If network is present user can use search view
     **/
    private fun initNetworkChangesListener() {
        NetworkUtils.getNetworkLiveData(requireContext())
            .observe(viewLifecycleOwner) { isConnected ->
                if (isConnected) {
                    offline_view.visibility = View.GONE
                    search_view.visibility = View.VISIBLE
                } else {
                    offline_view.visibility = View.VISIBLE
                    search_view.visibility = View.GONE
                }
            }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as SearchListenerActivity).showSearchView(false)
    }
}