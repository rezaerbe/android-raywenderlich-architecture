package com.erbe.mymvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erbe.mymvvm.data.MovieRepository
import com.erbe.mymvvm.data.MovieRepositoryImpl
import com.erbe.mymvvm.data.model.Movie

class SearchViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) :
    ViewModel() {

    fun searchMovie(query: String): LiveData<List<Movie>?> {
        return repository.searchMovies(query)
    }

    fun saveMovie(movie: Movie) {
        repository.saveMovie(movie)
    }
}