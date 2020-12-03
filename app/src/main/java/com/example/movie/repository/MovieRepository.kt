package com.example.movie.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.rxjava3.flowable
import com.example.movie.model.Cast
import com.example.movie.model.Movie
import com.example.movie.network.MovieService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val moviePagingCourse: MoviePagingSource
) {
    fun getTrendingMovie(): Flowable<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {moviePagingCourse}
        ).flowable
    }

    fun getMovieDetails(movieId: Long): Single<Movie>{
        return movieService.getMovie(movieId)
            .subscribeOn(Schedulers.io())
    }

    fun getCastDetails(castId: Long): Single<Cast>{
        return movieService.getCastDetails(castId)
            .subscribeOn(Schedulers.io())
    }
}