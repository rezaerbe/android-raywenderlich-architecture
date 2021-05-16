package com.erbe.myviper

import androidx.lifecycle.LiveData
import com.erbe.myviper.data.entity.Movie

interface SearchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMessage(msg: String)
        fun displayMovieList(movieList: List<Movie>)
        fun displayConfirmation(movie: Movie?)
    }

    interface Presenter {
        fun searchMovies(title: String)
        fun addMovieClicked(movie: Movie?)
        fun movieClicked(movie: Movie?)
        fun onDestroy()
    }

    interface Interactor {
        fun addMovie(movie: Movie?)
        fun searchMovies(title: String): LiveData<List<Movie>?>
    }

    interface InteractorOutput {
        fun onQuerySuccess(data: List<Movie>)
        fun onQueryError()
    }
}