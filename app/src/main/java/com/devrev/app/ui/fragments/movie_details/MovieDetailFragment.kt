package com.devrev.app.ui.fragments.movie_details

import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Color.alpha
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.devrev.app.TopMoviesViewModel
import com.devrev.app.databinding.FragmentMovieDetailBinding
import com.devrev.network.data.MovieDetailsResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding!!

    private var movieID = -1
    private val moviesViewModel: TopMoviesViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieID = arguments?.getInt(com.devrev.app.Constants.MOVIES_ID_KEY) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderView()
        setClickListeners()
        moviesViewModel.getMovieDetails(movieID)
    }

    private fun renderView() {
        moviesViewModel.movieDetails.observe(viewLifecycleOwner) { details ->
            details?.let {
                when (details) {
                    MovieDetailsResponse.Loading -> {
                        binding.groupAllElement.isGone = true
                        binding.progressCircular.isGone = false
                    }

                    is MovieDetailsResponse.Success -> {
                        binding.groupAllElement.isGone = false
                        binding.progressCircular.isGone = true
                        with(details.data) {
                            binding.tvMovieName.text = title
                            binding.tvMovieReleaseDate.text = release_date
                            binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$poster_path") {
                                crossfade(durationMillis = 1500)
                                transformations(RoundedCornersTransformation(12.5f))
                            }
                            binding.tvMovieOverview.text = overview
                            binding.ivBackGround.alpha = 0.2f
                            binding.ivBackGround.load("https://image.tmdb.org/t/p/w500/$backdrop_path"){
                                crossfade(1000)
                                scale(Scale.FILL)
                            }
                            val process = (vote_average*100).toFloat()
                            binding.tvProgress.text = "${process.toString().substring(0, 2)}%"
                            binding.circularProgressBar.setProgressWithAnimation(process, 1000)
                        }

                    }

                    is MovieDetailsResponse.Error -> {
                        binding.groupAllElement.isGone = false
                        binding.progressCircular.isGone = true
                        Toast.makeText(requireContext(), details.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
       binding.toolbar.setNavigationOnClickListener{ requireActivity().onBackPressed() }
        binding.btnMoviePlay.setOnClickListener { }
      //  binding.tvMovieInfo.setOnClickListener { }
        binding.tvMovieDownload.setOnClickListener { }
        binding.tvMovieAdvance.setOnClickListener { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}