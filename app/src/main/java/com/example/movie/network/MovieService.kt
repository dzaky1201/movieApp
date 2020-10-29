package com.example.movie.network

import com.example.movie.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovies(): Single<Movies>
}