package com.example.movie.repository

import com.example.movie.model.Movies
import com.example.movie.network.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {
    fun getTrendingMovie(): Single<Movies>{
        return movieService.getTrendingMovies()
            .subscribeOn(Schedulers.io())
    }
}