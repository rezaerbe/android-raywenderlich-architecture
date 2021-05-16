package com.erbe.mymvc.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_KEY = "e94174e69cda2306465bf88f320a17f7"
    const val TMDB_BASE_URL = "http://api.themoviedb.org/3/"
    const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"

    val moviesApi = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)
}