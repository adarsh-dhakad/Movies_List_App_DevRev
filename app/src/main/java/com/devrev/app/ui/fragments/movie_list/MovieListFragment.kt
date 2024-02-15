package com.devrev.app.ui.fragments.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.devrev.app.Constants
import com.devrev.app.R
import com.devrev.app.TopMoviesViewModel
import com.devrev.app.databinding.FragmentMovieListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private val moviesViewModel: TopMoviesViewModel by viewModel()
    private var adapter: MoviesAdapter? = null
    private var fragmentType = Constants.FRAGMENT_TYPE_LATEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentType = arguments?.getString(Constants.FRAGMENT_TYPE)?:Constants.FRAGMENT_TYPE_LATEST
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectUiState()
    }

    private fun initView() {
        adapter = MoviesAdapter()

        binding.rvMovies.isGone = false
        binding.tvMoviesEmpty.isGone = true
        binding.btnMoviesRetry.isGone = true
        binding.progressBarMovies.isGone = true
        binding.rvMovies.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter?.retry() },
            footer = MovieLoadStateAdapter { adapter?.retry() }
        )

       adapter?.addLoadStateListener { loadState -> renderUi(loadState) }

        binding.btnMoviesRetry.setOnClickListener { adapter?.retry() }

    }

    private fun collectUiState() {
        GlobalScope.launch(Dispatchers.IO) {
           if(fragmentType == Constants.FRAGMENT_TYPE_LATEST) {
               moviesViewModel.getLatestMovies().collectLatest { movies ->
                   adapter?.submitData(movies)
               }
           }else{
               moviesViewModel.getPopularMovies().collectLatest {
                   movies ->
                   adapter?.submitData(movies)
               }
           }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0
        binding.btnMoviesRetry.isVisible = loadState.source.refresh is LoadState.Error
        if(adapter?.itemCount != 0 && loadState.refresh is LoadState.NotLoading) {
         //   binding.rvMovies.isVisible = !isListEmpty
            binding.tvMoviesEmpty.isVisible = isListEmpty
          //  binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
        }else{
            binding.progressBarMovies.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }

    companion object{
        fun newInstance(type:String):MovieListFragment{
            return MovieListFragment().apply {
                arguments = bundleOf(Constants.FRAGMENT_TYPE to type)
            }
        }
    }
}