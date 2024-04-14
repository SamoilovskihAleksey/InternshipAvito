package com.internshipavito.presentation.gridFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.internshipavito.data.network.GalleryItem
import com.internshipavito.data.network.MoviePagingSource
import com.internshipavito.data.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class MovieGalleryViewModel(private val retrofitClient: RetrofitClient): ViewModel() {

    val getMovies: Flow<PagingData<GalleryItem>> = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(retrofitClient) }
    ).flow

    suspend fun findMoviesById(query: String): List<GalleryItem> {
        return retrofitClient.findMoviesById(query)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }


    class MovieViewModelFactory(private val retrofitClient: RetrofitClient) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieGalleryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieGalleryViewModel(retrofitClient) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}