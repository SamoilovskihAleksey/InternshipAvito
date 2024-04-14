package com.internshipavito.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState

class MoviePagingSource(
    private val retrofitClient: RetrofitClient
) : PagingSource<Int, GalleryItem>() {

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {

        val page = params.key ?: 1
        val limit = params.loadSize.coerceAtMost(20)

        val response = retrofitClient.fetchMovies(page, limit)
        val nextKey = if (response.size < limit) null else page + 1
        val prevKey = if (page == 1) null else page - 1
        return LoadResult.Page(response, prevKey, nextKey)
    }
}