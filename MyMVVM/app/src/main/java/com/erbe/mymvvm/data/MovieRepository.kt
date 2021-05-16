package com.erbe.mymvvm.data

import androidx.lifecycle.LiveData
import com.erbe.mymvvm.data.model.Movie

interface MovieRepository {

    fun getSavedMovies(): LiveData<List<Movie>>

    fun saveMovie(movie: Movie)

    fun deleteMovie(movie: Movie)

    fun searchMovies(query: String): LiveData<List<Movie>?>
}