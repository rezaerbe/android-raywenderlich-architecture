package com.erbe.myviper.interactor

import androidx.lifecycle.LiveData
import com.erbe.myviper.SearchContract
import com.erbe.myviper.data.MovieRepositoryImpl
import com.erbe.myviper.data.entity.Movie

class SearchInteractor : SearchContract.Interactor {

    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    override fun searchMovies(title: String): LiveData<List<Movie>?> =
        repository.searchMovies(title)

    override fun addMovie(movie: Movie?) {
        movie?.let {
            repository.saveMovie(movie)
        }
    }
}