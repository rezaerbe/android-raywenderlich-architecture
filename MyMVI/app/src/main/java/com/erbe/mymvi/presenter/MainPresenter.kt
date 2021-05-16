package com.erbe.mymvi.presenter

import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(private val movieInteractor: MovieInteractor) {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MainView

    fun bind(view: MainView) {
        this.view = view
        compositeDisposable.add(observeMovieDeleteIntent())
        compositeDisposable.add(observeMovieDisplay())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeMovieDeleteIntent() = view.deleteMovieIntent()
        .doOnNext { Timber.d("Intent: delete movie") }
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .flatMap<Unit> { movieInteractor.deleteMovie(it) }
        .subscribe()

    private fun observeMovieDisplay() = view.displayMoviesIntent()
        .doOnNext { Timber.d("Intent: display movies intent") }
        .flatMap<MovieState> { movieInteractor.getMovieList() }
        .startWith(MovieState.LoadingState)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }
}