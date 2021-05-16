package com.erbe.myviper

import com.erbe.myviper.data.entity.Movie

interface AddContract {

    interface View {
        fun showMessage(msg: String)
        fun searchMovieClicked(view: android.view.View)
        fun addMovieClicked(view: android.view.View)
    }

    interface Presenter {
        fun addMovies(title: String, year: String)
        fun searchMovies(title: String)
        fun onDestroy()
    }

    interface Interactor {
        fun addMovie(movie: Movie)
    }
}