package com.erbe.myviper.presenter

import androidx.lifecycle.Observer
import com.erbe.myviper.MainContract
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.view.activities.AddMovieActivity
import com.erbe.myviper.view.activities.MainActivity
import ru.terrakok.cicerone.Router
import java.util.*

class MainPresenter(
    private var view: MainContract.View?,
    private var interactor: MainContract.Interactor?,
    private val router: Router?
) : MainContract.Presenter, MainContract.InteractorOutput {

    override fun addMovie() {
        router?.navigateTo(AddMovieActivity.TAG)
    }

    override fun deleteMovies(selectedMovies: HashSet<*>) {
        for (movie in selectedMovies) {
            interactor?.delete(movie as Movie)
        }
    }

    override fun onViewCreated() {
        view?.showLoading()
        interactor?.loadMovieList()?.observe((view as MainActivity), Observer { movieList ->
            if (movieList != null) {
                onQuerySuccess(movieList)
            } else {
                onQueryError()
            }
        })
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
        view?.showMessage("Error Loading Data")
    }
}