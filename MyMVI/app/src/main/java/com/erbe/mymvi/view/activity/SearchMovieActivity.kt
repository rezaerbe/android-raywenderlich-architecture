package com.erbe.mymvi.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erbe.mymvi.R
import com.erbe.mymvi.action
import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.presenter.SearchPresenter
import com.erbe.mymvi.snack
import com.erbe.mymvi.view.SearchView
import com.erbe.mymvi.view.adapter.SearchListAdapter
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask
import timber.log.Timber

class SearchMovieActivity : BaseActivity(), SearchView {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private lateinit var presenter: SearchPresenter

    private val publishSubject: PublishSubject<Movie> = PublishSubject.create<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        searchRecyclerView.adapter = SearchListAdapter(emptyList())
        presenter = SearchPresenter(MovieInteractor())
        presenter.bind(this)

    }

    override fun displayMoviesIntent(): Observable<String> =
        Observable.just(intent.extras?.getString("title"))


    override fun addMovieIntent(): Observable<Movie> =
        (searchRecyclerView.adapter as SearchListAdapter).getViewClickObservable()

    override fun confirmIntent(): Observable<Movie> = publishSubject


    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun render(state: MovieState) {
        when (state) {
            is MovieState.LoadingState -> renderLoadingState()
            is MovieState.DataState -> renderDataState(state)
            is MovieState.ErrorState -> renderErrorState(state)
            is MovieState.ConfirmationState -> renderConfirmationState(state)
            is MovieState.FinishState -> renderFinishState()
        }
    }

    private fun renderFinishState() {
        Timber.d("Render: finish state")
        startActivity(intentFor<MainActivity>().newTask().clearTask())
    }


    private fun renderLoadingState() {
        Timber.d("Render: loading state")
        searchRecyclerView.isEnabled = false
        searchProgressBar.visibility = View.VISIBLE
    }

    private fun renderConfirmationState(confirmationState: MovieState.ConfirmationState) {
        Timber.d("Render: confirm state")
        searchLayout.snack(
            "Add ${confirmationState.movie.title} to your list?",
            Snackbar.LENGTH_LONG
        ) {
            action(getString(R.string.ok)) {
                publishSubject.onNext(confirmationState.movie)
            }
        }
    }

    private fun renderDataState(dataState: MovieState.DataState) {
        Timber.d("Render: data state")
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.apply {
            isEnabled = true
            (adapter as SearchListAdapter).setMovies(dataState.data)
        }
    }

    private fun renderErrorState(errorState: MovieState.ErrorState) {
        Timber.d("Render: error state")
        searchProgressBar.visibility = View.GONE
        longToast(errorState.data)
    }
}