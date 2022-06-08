package com.example.usersearchapplication.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersearchapplication.R
import com.example.usersearchapplication.data.model.SearchItem
import com.example.usersearchapplication.databinding.ItemSearchBinding
import com.example.usersearchapplication.ui.interfaces.OnItemClickListener
import com.example.usersearchapplication.ui.interfaces.OnLoadMoreListener
import com.example.usersearchapplication.utils.AppConstant

/**
 * @Author: Akash Abhishek
 * @Date: 07 June 2022
 */

class SearchAdapter(recyclerView: RecyclerView) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var onLoadMoreListener: OnLoadMoreListener
    lateinit var onItemClickListener: OnItemClickListener

    private var searchItem: MutableList<SearchItem> = mutableListOf()

    private var loading = false
    private var isLastPage = false

    // Logic for pagination
    init {

        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val firstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition()
                    val visibleChildCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    if (!loading && !isLastPage) {
                        if ((firstVisibleItemPosition + visibleChildCount) >= totalItemCount
                            && firstVisibleItemPosition > 0
                            && totalItemCount >= AppConstant.PAGE_SIZE
                        ) {
                            loading = true
                            onLoadMoreListener.onLoadMore()
                        }
                    }
                }
            })
        }
    }

    fun updateItems(items: List<SearchItem>) {
        items.forEach { this.searchItem.add(it) }
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.searchItem.clear()
        notifyDataSetChanged()
    }

    fun setLoaded() {
        loading = false
    }

    fun setLastPage(value: Boolean) {
        isLastPage = value
    }

    override fun getItemCount() = searchItem.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.item = searchItem[position]
        holder.binding.searchItem.setOnClickListener {
            onItemClickListener.onItemClick(searchItem[position])
        }

    }

    class SearchViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}