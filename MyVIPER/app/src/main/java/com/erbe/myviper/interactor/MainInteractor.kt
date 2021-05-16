package com.erbe.myviper.interactor

import androidx.lifecycle.MediatorLiveData
import com.erbe.myviper.MainContract
import com.erbe.myviper.data.MovieRepositoryImpl
import com.erbe.myviper.data.entity.Movie

class MainInteractor : MainContract.Interactor {

    private val movieList = MediatorLiveData<List<Movie>>()
    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    init {
        getAllMovies()
    }

    override fun loadMovieList() = movieList

    override fun delete(movie: Movie) = repository.deleteMovie(movie)

    override fun getAllMovies() {
        movieList.addSource(repository.getSavedMovies()) { movies ->
            movieList.postValue(movies)
        }
    }
}