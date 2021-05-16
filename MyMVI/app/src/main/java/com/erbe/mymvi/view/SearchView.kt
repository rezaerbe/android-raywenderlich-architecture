package com.erbe.mymvi.view

import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import io.reactivex.Observable

interface SearchView {
    fun render(state: MovieState)
    fun addMovieIntent(): Observable<Movie>
    fun confirmIntent(): Observable<Movie>
    fun displayMoviesIntent(): Observable<String>
}