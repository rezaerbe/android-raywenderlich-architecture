package com.erbe.mymvvm.data.net

import com.erbe.mymvvm.data.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key: String): Call<MoviesResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") q: String
    ): Call<MoviesResponse>
}