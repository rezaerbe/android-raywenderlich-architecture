package com.erbe.mymvi.data

import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import io.reactivex.Observable

interface Interactor {
    fun getMovieList(): Observable<MovieState>
    fun deleteMovie(movie: Movie): Observable<Unit>
    fun searchMovies(title: String): Observable<MovieState>
    fun addMovie(movie: Movie): Observable<MovieState>
}