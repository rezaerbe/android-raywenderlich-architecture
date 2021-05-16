package com.erbe.mymvi.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.erbe.mymvi.R
import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.presenter.MainPresenter
import com.erbe.mymvi.view.MainView
import com.erbe.mymvi.view.adapter.MovieListAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import timber.log.Timber

class MainActivity : BaseActivity(), MainView {
    override fun displayMoviesIntent(): Observable<Unit> = Observable.just(Unit)

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesRecyclerView.adapter = MovieListAdapter(emptyList())
        presenter = MainPresenter(MovieInteractor())
        presenter.bind(this)
    }

    override fun deleteMovieIntent(): Observable<Movie> {
        val observable = Observable.create<Movie> { emitter ->
            // Add the functionality to swipe items in the
            // recycler view to delete that item
            val helper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    // We are not implementing onMove() in this app
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.getAdapterPosition()
                        val movie =
                            (moviesRecyclerView.adapter as MovieListAdapter).getMoviesAtPosition(
                                position
                            )
                        emitter.onNext(movie)
                    }
                })

            helper.attachToRecyclerView(moviesRecyclerView)
        }
        return observable
    }

    fun goToAddActivity(view: View) = startActivity<AddMovieActivity>()

    override fun render(state: MovieState) {
        when (state) {
            is MovieState.LoadingState -> renderLoadingState()
            is MovieState.DataState -> renderDataState(state)
            is MovieState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {
        Timber.d("Render: loading state")
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(dataState: MovieState.DataState) {
        Timber.d("Render: data state")
        progressBar.visibility = View.GONE
        moviesRecyclerView.apply {
            isEnabled = true
            (adapter as MovieListAdapter).setMovies(dataState.data)
        }
    }

    private fun renderErrorState(dataState: MovieState.ErrorState) {
        Timber.d("Render: Error State")
        longToast(dataState.data)
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }
}