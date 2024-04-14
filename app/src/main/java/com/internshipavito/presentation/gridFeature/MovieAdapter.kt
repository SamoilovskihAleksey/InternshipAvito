package com.internshipavito.presentation.gridFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.internshipavito.data.network.GalleryItem
import com.internshipavito.databinding.GridItemBinding

class MovieGridAdapter : PagingDataAdapter<GalleryItem, MovieGridAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let {
            holder.bind(it)
        }
    }

    inner class MovieViewHolder(private val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: GalleryItem) {
            binding.itemMovieView.setImageURI(movie.poster.url)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }
        }
    }

    private var onItemClickListener: ((GalleryItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (GalleryItem) -> Unit) {
        onItemClickListener = listener
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean =
                oldItem == newItem
        }
    }
}