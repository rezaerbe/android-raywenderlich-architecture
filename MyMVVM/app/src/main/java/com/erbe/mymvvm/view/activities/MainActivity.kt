package com.erbe.mymvvm.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.erbe.mymvvm.R
import com.erbe.mymvvm.view.adapters.MovieListAdapter
import com.erbe.mymvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val adapter = MovieListAdapter(mutableListOf())
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        moviesRecyclerView.adapter = adapter
        showLoading()
        viewModel.getSavedMovies().observe(this, Observer { movies ->
            hideLoading()
            movies?.let {
                adapter.setMovies(movies)
            }
        })
    }

    private fun showLoading() {
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        moviesRecyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    private fun deleteMoviesClicked() {
        for (movie in adapter.selectedMovies) {
            viewModel.deleteSavedMovies(movie)
        }
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    fun goToAddActivity(view: View) = startActivity<AddMovieActivity>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> this.deleteMoviesClicked()
            else -> toast(getString(R.string.error))
        }
        return super.onOptionsItemSelected(item)
    }
}