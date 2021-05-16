package com.erbe.myviper.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erbe.myviper.*
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.interactor.SearchInteractor
import com.erbe.myviper.presenter.SearchPresenter
import com.erbe.myviper.view.adapters.SearchListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class SearchMovieActivity : BaseActivity(), SearchContract.View {

    private var presenter: SearchContract.Presenter? = null

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val router: Router? by lazy { App.INSTANCE.cicerone.router }

    private lateinit var adapter: SearchListAdapter

    companion object {
        val TAG: String = "SearchMovieActivity"
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Back) {
                    back()
                }
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                    MainActivity.TAG -> startActivity(
                        Intent(this@SearchMovieActivity, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }

            private fun back() {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        presenter = SearchPresenter(this, SearchInteractor(), router)
    }

    override fun showLoading() {
        searchProgressBar.visibility = View.VISIBLE
        searchRecyclerView.isEnabled = false
    }

    override fun hideLoading() {
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.isEnabled = true
    }

    override fun showMessage(msg: String) {
        searchLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                val title = intent.extras?.getString("title").toString()
                presenter?.searchMovies(title)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val title = intent.extras?.getString("title").toString()
        presenter?.searchMovies(title)
        App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun displayMovieList(movieList: List<Movie>) {
        adapter = SearchListAdapter(movieList) { movie -> presenter?.movieClicked(movie) }
        searchRecyclerView.adapter = adapter
    }

    override fun displayConfirmation(movie: Movie?) {
        searchLayout.snack("Add ${movie?.title} to your list?", Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
                presenter?.addMovieClicked(movie)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}