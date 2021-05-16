package com.erbe.mymvi.view

import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import io.reactivex.Observable

interface MainView {
    fun render(state: MovieState)
    fun deleteMovieIntent(): Observable<Movie>
    fun displayMoviesIntent(): Observable<Unit>
}