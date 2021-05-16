package com.erbe.myviper.presenter

import androidx.lifecycle.Observer
import com.erbe.myviper.SearchContract
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.view.activities.MainActivity
import com.erbe.myviper.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router

class SearchPresenter(
    private var view: SearchContract.View?,
    private var interactor: SearchContract.Interactor?,
    val router: Router?
) : SearchContract.Presenter, SearchContract.InteractorOutput {

    override fun searchMovies(title: String) {
        view?.showLoading()
        interactor?.searchMovies(title)
            ?.observe(view as SearchMovieActivity, Observer { movieLiest ->
                if (movieLiest == null) {
                    onQueryError()
                } else {
                    onQuerySuccess(movieLiest)
                }
            })
    }

    override fun addMovieClicked(movie: Movie?) {
        interactor?.addMovie(movie)
        router?.navigateTo(MainActivity.TAG)
    }

    override fun movieClicked(movie: Movie?) {
        view?.displayConfirmation(movie)
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onQuerySuccess(data: List<Movie>) {
        view?.hideLoading()
        view?.displayMovieList(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showMessage("Error")
    }
}