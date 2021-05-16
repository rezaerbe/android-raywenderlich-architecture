package com.erbe.mymvp.add

import com.erbe.mymvp.model.LocalDataSource
import com.erbe.mymvp.model.Movie

class AddMoviePresenter(
    private var viewInterface: AddMovieContract.ViewInterface,
    private var dataSource: LocalDataSource
) : AddMovieContract.PresenterInterface {

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie title cannot be empty")
        } else {
            val movie = Movie(title, releaseDate, posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }
}