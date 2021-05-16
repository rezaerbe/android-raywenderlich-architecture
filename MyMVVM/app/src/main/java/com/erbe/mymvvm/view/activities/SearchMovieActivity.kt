package com.erbe.mymvvm.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.erbe.mymvvm.R
import com.erbe.mymvvm.action
import com.erbe.mymvvm.data.model.Movie
import com.erbe.mymvvm.snack
import com.erbe.mymvvm.view.adapters.SearchListAdapter
import com.erbe.mymvvm.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SearchMovieActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private var adapter = SearchListAdapter(mutableListOf()) { movie -> displayConfirmation(movie) }

    private lateinit var viewModel: SearchViewModel

    private lateinit var title: String

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        intent?.extras?.getString("title")?.let {
            title = it
        }
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchRecyclerView.adapter = adapter
        searchMovie()
    }

    private fun showLoading() {
        searchProgressBar.visibility = View.VISIBLE
        searchRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.isEnabled = true
    }

    private fun showMessage() {
        searchLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                searchMovie()
            }
        }
    }

    private fun displayConfirmation(movie: Movie) {
        searchLayout.snack("Add ${movie.title} to your list?", Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
                viewModel.saveMovie(movie)
                startActivity(intentFor<MainActivity>().newTask().clearTask())
            }
        }
    }

    private fun searchMovie() {
        showLoading()
        viewModel.searchMovie(title).observe(this, Observer { movies ->
            hideLoading()
            if (movies == null) {
                showMessage()
            } else {
                adapter.setMovies(movies)
            }
        })
    }
}