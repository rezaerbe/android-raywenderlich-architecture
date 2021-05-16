package com.erbe.myviper

import androidx.lifecycle.LiveData
import com.erbe.myviper.data.entity.Movie
import java.util.*

interface MainContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMessage(msg: String)
        fun displayMovieList(movieList: List<Movie>)
        fun deleteMoviesClicked()
    }

    interface Presenter {
        fun deleteMovies(selectedMovies: HashSet<*>)
        fun onViewCreated()
        fun onDestroy()
        fun addMovie()
    }

    interface Interactor {
        fun loadMovieList(): LiveData<List<Movie>>
        fun delete(movie: Movie)
        fun getAllMovies()
    }

    interface InteractorOutput {
        fun onQuerySuccess(data: List<Movie>)
        fun onQueryError()
    }
}