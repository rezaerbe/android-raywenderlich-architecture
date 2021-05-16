package com.erbe.mymvi.data.net

import com.erbe.mymvi.data.model.MoviesResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val moviesApi: MoviesApi

    companion object {
        private const val API_KEY = "e94174e69cda2306465bf88f320a17f7"
        private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun searchMovies(query: String): Observable<MoviesResponse> {
        return moviesApi.searchMovie(API_KEY, query)
    }
}