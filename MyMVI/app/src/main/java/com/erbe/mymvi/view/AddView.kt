package com.erbe.mymvi.view

import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import io.reactivex.Observable

interface AddView {
    fun render(state: MovieState)
    fun addMovieIntent(): Observable<Movie>
}