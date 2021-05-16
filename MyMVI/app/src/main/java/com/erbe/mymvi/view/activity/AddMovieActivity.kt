package com.erbe.mymvi.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erbe.mymvi.R
import com.erbe.mymvi.action
import com.erbe.mymvi.data.MovieInteractor
import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.domain.MovieState
import com.erbe.mymvi.presenter.AddPresenter
import com.erbe.mymvi.snack
import com.erbe.mymvi.view.AddView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class AddMovieActivity : BaseActivity(), AddView {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val publishSubject: PublishSubject<Movie> = PublishSubject.create()

    private lateinit var presenter: AddPresenter

    override fun getToolbarInstance() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        presenter = AddPresenter(MovieInteractor())
        presenter.bind(this)
    }

    override fun addMovieIntent() = publishSubject

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun render(state: MovieState) {
        when (state) {
            is MovieState.FinishState -> renderFinishState()
        }
    }

    private fun renderFinishState() = startActivity(intentFor<MainActivity>().newTask().clearTask())

    fun goToSearchMovieActivity(view: View) {
        if (titleEditText.text.toString().isNotBlank()) {
            startActivity(intentFor<SearchMovieActivity>("title" to titleEditText.text.toString()))
        } else {
            showMessage("You must enter a title")
        }
    }

    fun showMessage(msg: String) {
        addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }

    fun addMovieClick(view: View) {
        if (titleEditText.text.toString().isNotBlank()) {
            publishSubject.onNext(
                Movie(
                    title = titleEditText.text.toString(),
                    releaseDate = yearEditText.text.toString()
                )
            )
        } else {
            showMessage("You must enter a title")
        }
    }
}