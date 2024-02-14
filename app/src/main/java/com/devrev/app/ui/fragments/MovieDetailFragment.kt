package com.devrev.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devrev.app.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding!!

    private var movieID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieID = arguments?.getInt(com.devrev.app.Constants.MOVIES_KEY) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderView()
        setClickListeners()
    }

    private fun renderView() {
//        with(args.movie) {
//            binding.tvMovieName.text = title
//            binding.tvMovieOverview.text = overview
//            binding.tvMovieReleaseDate.text = releaseDate
//            binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$image") {
//                crossfade(durationMillis = 1500)
//                transformations(RoundedCornersTransformation(12.5f))
//            }
//        }
    }

    private fun setClickListeners() {
        binding.ivMovieDetailClose.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnMoviePlay.setOnClickListener { }
        binding.tvMovieInfo.setOnClickListener { }
        binding.tvMovieDownload.setOnClickListener { }
        binding.tvMovieAdvance.setOnClickListener { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}