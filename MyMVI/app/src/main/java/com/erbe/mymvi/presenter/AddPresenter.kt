package com.erbe.mymvi.presenter

import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.view.AddView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddPresenter(private val movieInteractor: MovieInteractor) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: AddView

    fun bind(view: AddView) {
        this.view = view
        compositeDisposable.add(observeAddMovieIntent())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun observeAddMovieIntent() = view.addMovieIntent()
        .observeOn(Schedulers.io())
        .flatMap<MovieState> { movieInteractor.addMovie(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }
}