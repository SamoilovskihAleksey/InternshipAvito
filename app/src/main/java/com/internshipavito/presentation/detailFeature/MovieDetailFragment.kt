package com.internshipavito.presentation.detailFeature

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.internshipavito.R
import com.internshipavito.data.network.GalleryItem
import com.internshipavito.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailFragment: Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    private val movieDetailViewmodel: MovieDetailViewmodel by viewModels{
        MovieDetailViewModelFactory(args.movieId)
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewmodel.galleryItem.collect { movie ->
                    movie?.let { updateUi(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(movie: GalleryItem) {
        binding.apply {
            moviePoster.setImageURI(movie.poster.url)
            movieName.text = movie.name
            movieYear.text = getString(R.string.production_year) + " " + movie.year
            movieDescription.text = movie.description
            movieRatingKp.text = if (movie.rating.kp == "0") getString(R.string.movie_rating_stub) else "Кинопоиск: ${movie.rating.kp}"
            movieRatingImdb.text = if (movie.rating.imdb == "0") getString(R.string.movie_rating_stub) else "Imdb: ${movie.rating.imdb}"
            movieRatingFc.text = if (movie.rating.filmCritics == "0") getString(R.string.movie_rating_stub) else "FilmCritics: ${movie.rating.filmCritics}"
            }

        }
    }