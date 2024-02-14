package com.devrev.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devrev.app.databinding.DashboardMovieItemBinding
import com.devrev.app.model.MovieUi
import coil.load
import coil.transform.RoundedCornersTransformation

class MoviesAdapter : PagingDataAdapter<MovieUi, MoviesAdapter.MoviePosterViewHolder>(movieDiffCallBack) {

    class MoviePosterViewHolder(val binding: DashboardMovieItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(path: String?) {
            path?.let {
                binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$it") {
                    crossfade(durationMillis = 2000)
                    transformations(RoundedCornersTransformation(12.5f))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {
        val holder = MoviePosterViewHolder(
            DashboardMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.binding.root.setOnClickListener { view ->
            getItem(holder.absoluteAdapterPosition)?.let { movieUi ->
//                view.findNavController().navigate(
//                    MovieListFragmentDirections.actionGoToSheetDetail(
//                        movie = movieUi
//                    )
//                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        holder.bind(getItem(position)?.image)
    }

}



private val  movieDiffCallBack = object : DiffUtil.ItemCallback<MovieUi>() {
    override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem.id == newItem.id
    }
}