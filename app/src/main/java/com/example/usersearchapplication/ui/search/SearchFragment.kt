package com.example.usersearchapplication.ui.search

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersearchapplication.base.BaseFragment
import com.example.usersearchapplication.data.model.SearchItem
import com.example.usersearchapplication.databinding.FragmentSearchBinding
import com.example.usersearchapplication.ui.interfaces.OnItemClickListener
import com.example.usersearchapplication.ui.interfaces.OnLoadMoreListener
import com.example.usersearchapplication.ui.interfaces.SearchListenerActivity
import com.example.usersearchapplication.ui.interfaces.SearchListenerFragment
import com.example.usersearchapplication.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
    SearchListenerFragment, OnLoadMoreListener, OnItemClickListener {

    override val viewModel: SearchViewModel by viewModels()

    // Navigation
    private val args: SearchFragmentArgs by navArgs()

    private lateinit var searchQuery: String
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = args.searchQuery

        (requireActivity() as SearchListenerActivity).registerSearchFragment(this)
        (requireActivity() as SearchListenerActivity).setSearchText(searchQuery)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as SearchListenerActivity).showSearchView(true)
    }

    // casual ui binds
    override fun bindUI() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        swipeRefreshLayout.setOnRefreshListener {
            getData()
        }

        searchAdapter = SearchAdapter(recycler_view)
        searchAdapter.onItemClickListener = this
        searchAdapter.onLoadMoreListener = this
        recycler_view.adapter = searchAdapter
    }

    override fun callApi() {
        super.callApi()
        getData()
    }

    // Request data by search
    private fun getData() {
        viewModel.resetData()
        searchAdapter.clearItems()
        viewModel.getSearchResults(searchQuery)
    }

    // Observe data & update UI
    override fun initReposObserver() {
        viewModel.response.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false
            searchAdapter.setLastPage(viewModel.isLastPage())
            searchAdapter.setLoaded()
            binding.apply {
                itemSize = it.data?.items?.size
            }
            it.data?.items?.let { it1 -> searchAdapter.updateItems(it1) }
        }
    }

    override fun doSearch(searchQuery: String) {
        this.searchQuery = searchQuery
        getData()
    }

    override fun onLoadMore() {
        viewModel.onLoadMore(this.searchQuery)
    }

    override fun onItemClick(item: SearchItem) {
        val action =
            SearchFragmentDirections.actionDetails(
                item
            )
        navigate(action)
    }

    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)
}