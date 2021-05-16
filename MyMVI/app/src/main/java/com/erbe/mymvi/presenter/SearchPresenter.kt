package com.erbe.mymvi.presenter

import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.view.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchPresenter(private val movieInteractor: MovieInteractor) {
    private val compositeDisposable = CompositeDisposable()

    private lateinit var view: SearchView

    fun bind(view: SearchView) {
        this.view = view
        compositeDisposable.add(observeMovieDisplayIntent())
        compositeDisposable.add(observeAddMovieIntent())
        compositeDisposable.add(observeConfirmIntent())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeConfirmIntent() = view.confirmIntent()
        .doOnNext { Timber.d("Intent: confirm") }
        .observeOn(Schedulers.io())
        .flatMap<MovieState> { movieInteractor.addMovie(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }


    private fun observeAddMovieIntent() = view.addMovieIntent()
        .doOnNext { Timber.d("Intent: add movie") }
        .map<MovieState> { MovieState.ConfirmationState(it) }
        .subscribe { view.render(it) }


    private fun observeMovieDisplayIntent() = view.displayMoviesIntent()
        .doOnNext { Timber.d("Intent: display movies intent") }
        .flatMap<MovieState> { movieInteractor.searchMovies(it) }
        .startWith(MovieState.LoadingState)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }
}