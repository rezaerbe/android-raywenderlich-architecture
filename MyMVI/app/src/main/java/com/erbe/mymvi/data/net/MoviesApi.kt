package com.erbe.mymvi.data.net

import com.erbe.mymvi.data.model.MoviesResponse
import io.reactivex.Observable
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
    ): Observable<MoviesResponse>
}