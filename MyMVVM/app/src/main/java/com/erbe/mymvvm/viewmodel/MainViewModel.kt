package com.erbe.mymvvm.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.erbe.mymvvm.data.MovieRepository
import com.erbe.mymvvm.data.MovieRepositoryImpl
import com.erbe.mymvvm.data.model.Movie

class MainViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) : ViewModel() {

    private val allMovies = MediatorLiveData<List<Movie>>()

    init {
        getAllMovies()
    }

    fun getSavedMovies() = allMovies

    private fun getAllMovies() {
        allMovies.addSource(repository.getSavedMovies()) { movies ->
            allMovies.postValue(movies)
        }
    }

    fun deleteSavedMovies(movie: Movie) {
        repository.deleteMovie(movie)
    }
}