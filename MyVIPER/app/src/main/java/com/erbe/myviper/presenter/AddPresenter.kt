package com.erbe.myviper.presenter

import com.erbe.myviper.AddContract
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.view.activities.MainActivity
import com.erbe.myviper.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router

class AddPresenter(
    private var view: AddContract.View?,
    private var interactor: AddContract.Interactor?,
    private val router: Router?
) : AddContract.Presenter {

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun addMovies(title: String, year: String) {
        if (title.isNotBlank()) {
            router?.navigateTo(MainActivity.TAG)
            val movie = Movie(title = title, releaseDate = year)
            interactor?.addMovie(movie)
        } else {
            view?.showMessage("You must enter a title")
        }
    }

    override fun searchMovies(title: String) {
        if (title.isNotBlank()) {
            router?.navigateTo(SearchMovieActivity.TAG)
        } else {
            view?.showMessage("You must enter a title")
        }
    }
}