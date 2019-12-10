package com.example.newsapp

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreRecyclerViewListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {

    private var loading = true
    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            if (hasMore(totalItemCount)) {
                loading = true
                onLoadMore(++currentPage)
            }
        }
    }

    private fun hasMore(totalItemCount: Int): Boolean {
        return true
    }

    fun reset() {
        loading = true
        currentPage = 1
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    abstract fun onLoadMore(currentPage: Int)

    companion object {
        private val VISIBLE_THRESHOLD = 5
    }
}