package com.example.movie.ui.landing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.model.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_landing.*
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
        rvMovie.adapter = movieAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        landingViewModel.trendingMovies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                    movieAdapter.setMovies(it.data!!)
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true)
            }

        })
    }

    private fun showLoading(isShow: Boolean){
        if (isShow){
            loadingContainer.visibility = View.VISIBLE
        }else {
            loadingContainer.visibility = View.GONE
        }
    }
}