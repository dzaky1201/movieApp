package com.example.movie.network

import com.example.movie.model.Movie
import com.example.movie.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovies(@Query("page") page: Int): Single<Movies>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(@Path("movieId") movieId: Long): Single<Movie>
}