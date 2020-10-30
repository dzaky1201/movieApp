package com.example.movie.ui.landing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.movie.model.Movie
import com.example.movie.model.Resource
import com.example.movie.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class LandingViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _trendingMovies = MutableLiveData<PagingData<Movie>>()
    val trendingMovies: LiveData<PagingData<Movie>>
        get() = _trendingMovies
    
    init {
        onGetTrendingMovie()
    }

    override fun onCleared() {
        //To Avoid memory leak
        compositeDisposable.clear()
        super.onCleared()

    }

    fun onRefresh() {
        onGetTrendingMovie()
    }

    private fun onGetTrendingMovie(){
        compositeDisposable.add(
            movieRepository.getTrendingMovie()
                .cachedIn(viewModelScope)
                .subscribe {
                    _trendingMovies.value = it
                }
        )
    }


}