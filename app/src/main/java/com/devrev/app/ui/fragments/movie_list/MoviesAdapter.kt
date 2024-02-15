package com.devrev.app.ui.fragments.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.devrev.app.Constants
import com.devrev.app.R
import com.devrev.app.databinding.DashboardMovieItemBinding
import com.devrev.app.model.MovieUi
import kotlinx.coroutines.Dispatchers

class MoviesAdapter : PagingDataAdapter<MovieUi, MoviesAdapter.MoviePosterViewHolder>(
    movieDiffCallBack
) {

    class MoviePosterViewHolder(val binding: DashboardMovieItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(movie: MovieUi?) {

            binding.tvMovieName.text = movie?.title

            movie?.image?.let {
                binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$it") {
                    crossfade(durationMillis = 2000)
                    transformations(RoundedCornersTransformation(13f))
                    dispatcher(Dispatchers.IO)
               //     error(R.drawable.ic_movie)
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
                view.findNavController().navigate(
                    R.id.action_go_to_detail,
                    bundleOf(Constants.MOVIES_ID_KEY to movieUi.id)
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

private val  movieDiffCallBack = object : DiffUtil.ItemCallback<MovieUi>() {
    override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title
                && oldItem.createdAt == newItem.createdAt
    }

    override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem == newItem
    }
}