package com.internshipavito.presentation.detailFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.internshipavito.data.network.GalleryItem
import com.internshipavito.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewmodel(movieId: String) : ViewModel() {

    private val retrofitClient = RetrofitClient()

    private val _galleryItem: MutableStateFlow<GalleryItem?> = MutableStateFlow(null)
    val galleryItem: StateFlow<GalleryItem?>
        get() = _galleryItem.asStateFlow()

    init {
        viewModelScope.launch {
            _galleryItem.value = retrofitClient.fetchMovie(movieId)
        }
    }
}

class MovieDetailViewModelFactory(
        private val movieId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailViewmodel(movieId) as T
        }
    }
