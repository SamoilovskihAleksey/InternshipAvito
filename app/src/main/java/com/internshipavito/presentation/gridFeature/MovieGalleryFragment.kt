package com.internshipavito.presentation.gridFeature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.internshipavito.data.network.RetrofitClient
import com.internshipavito.databinding.FragmentMovieGalleryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MovieGalleryFragment: Fragment() {

    private var _binding: FragmentMovieGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null"
        }

    private var searchJob: Job? = null

    private val retrofitClient = RetrofitClient()

    private val movieGalleryViewModel: MovieGalleryViewModel by viewModels { MovieGalleryViewModel.MovieViewModelFactory(retrofitClient) }

    private val movieGridAdapter = MovieGridAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGalleryBinding.inflate(inflater, container, false)
        binding.movieGrid.layoutManager = GridLayoutManager(context, 2)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieGrid.adapter = movieGridAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieGalleryViewModel.getMovies.collectLatest { pagingData ->
                    movieGridAdapter.submitData(pagingData)
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    if (!newText.isNullOrBlank()) {
                        searchMovie(newText)
                    } else {
                        movieGalleryViewModel.getMovies.collectLatest { pagingData ->
                            movieGridAdapter.submitData(pagingData)

                        }
                    }
                }
                return true
            }

        })

        movieGridAdapter.setOnItemClickListener {
            val direction = MovieGalleryFragmentDirections.showMovieDetails(it.id)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchMovie(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val foundMovies = movieGalleryViewModel.findMoviesById(query)
                movieGridAdapter.submitData(PagingData.from(foundMovies))
            } catch (ex: Exception) {
                Log.e("MovieGalleryFragment", "Error searching for movies: $ex")
            }
        }
    }
}