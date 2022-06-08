package com.example.usersearchapplication.ui.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.usersearchapplication.base.BaseFragment
import com.example.usersearchapplication.data.model.SearchItem
import com.example.usersearchapplication.databinding.FragmentDetailsBinding
import com.example.usersearchapplication.ui.interfaces.SearchListenerActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailsBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModels()

    private var searchItem: SearchItem? = null

    // Navigation
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchItem = args.searchItem
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as SearchListenerActivity).showSearchView(false)
    }

    override fun callApi() {
        super.callApi()
        searchItem?.login?.let { viewModel.getUserDetail(it) }
    }


    // Observe data & update UI
    override fun initReposObserver() {
        viewModel.response.observe(viewLifecycleOwner) {
            binding.apply {
                item = searchItem
                userDetail = it.data
            }
        }
    }


    override fun getViewBinding(): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater)
}