package com.example.movie.ui.landing

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.model.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_landing.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_loading.*
import timber.log.Timber


@AndroidEntryPoint
class LandingFragment : Fragment(R.layout.fragment_landing) {

    private lateinit var movieAdapter: MovieAdapter
    private val landingViewModel by viewModels<LandingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()
        rvMovie.layoutManager = LinearLayoutManager(requireContext())
        rvMovie.adapter = movieAdapter.withLoadStateFooter(
            MovieFooterStateAdapter{
                movieAdapter.retry()
            }
        )

        movieAdapter.addLoadStateListener { loadStates ->
            srl.isRefreshing = loadStates.source.refresh is LoadState.Loading
            IIErrorContainer.isVisible = loadStates.source.refresh is LoadState.Error
            rvMovie.isVisible = !IIErrorContainer.isVisible

            if (loadStates.source.refresh is LoadState.Error) {
                btnRetry.setOnClickListener {
                    movieAdapter.retry()
                }

                IIErrorContainer.isVisible = loadStates.source.refresh is LoadState.Error
                val errorMessage = "Cek Koneksi anda"
                tvErrorMessage.text = errorMessage
            }
        }

        srl.setOnRefreshListener {
            landingViewModel.onRefresh()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        landingViewModel.trendingMovies.observe(viewLifecycleOwner, {
            movieAdapter.submitData(lifecycle, it)
        })
    }


}