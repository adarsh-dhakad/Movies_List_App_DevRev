package com.devrev.app.ui.fragments.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.devrev.app.R
import com.devrev.app.TopMoviesViewModel
import com.devrev.app.databinding.FragmentMovieListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private val moviesViewModel: TopMoviesViewModel by viewModel()
    private var adapter: MoviesAdapter? = null

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

        binding.rvMovies.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter?.retry() },
            footer = MovieLoadStateAdapter { adapter?.retry() }
        )

        adapter?.addLoadStateListener { loadState -> renderUi(loadState) }

        binding.btnMoviesRetry.setOnClickListener { adapter?.retry() }

    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.getTopMovies().collectLatest { movies ->

                Log.d("devKey" , "rr"+movies.toString())
                adapter?.submitData(movies)
            }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

        binding.rvMovies.isVisible = !isListEmpty
        binding.tvMoviesEmpty.isVisible = isListEmpty
        binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
        binding.progressBarMovies.isVisible = loadState.source.refresh is LoadState.Loading
        binding.btnMoviesRetry.isVisible = loadState.source.refresh is LoadState.Error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }

    companion object{
        fun newInstance(type:Int):MovieListFragment{
            return MovieListFragment().apply {
                arguments = bundleOf("type" to type)
            }
        }
    }
}