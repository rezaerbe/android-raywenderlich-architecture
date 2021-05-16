package com.erbe.mymvp.main

import com.erbe.mymvp.model.Movie
import java.util.*

class MainContract {

    interface PresenterInterface {
        fun getMyMoviesList()
        fun onDeleteTapped(selectedMovies: HashSet<Movie>)
        fun stop()
    }

    interface ViewInterface {
        fun displayMovies(movieList: List<Movie>)
        fun displayNoMovies()
        fun showToast(string: String)
        fun displayError(string: String)
    }
}