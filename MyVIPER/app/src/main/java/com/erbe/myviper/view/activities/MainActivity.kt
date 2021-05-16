package com.erbe.myviper.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erbe.myviper.App
import com.erbe.myviper.MainContract
import com.erbe.myviper.R
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.interactor.MainInteractor
import com.erbe.myviper.presenter.MainPresenter
import com.erbe.myviper.view.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class MainActivity : BaseActivity(), MainContract.View {

    companion object {
        val TAG: String = "MainActivity"
    }

    lateinit var presenter: MainContract.Presenter
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private lateinit var adapter: MovieListAdapter
    private val router: Router? by lazy { App.INSTANCE.cicerone.router }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, MainInteractor(), router)
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {   // 2
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                    AddMovieActivity.TAG -> startActivity(
                        Intent(
                            this@MainActivity,
                            AddMovieActivity::class.java
                        )
                    )
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    override fun showLoading() {
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        moviesRecyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated()
        App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }

    override fun deleteMoviesClicked() {
        presenter.deleteMovies(adapter.selectedMovies)
    }

    override fun displayMovieList(movieList: List<Movie>) {
        adapter = MovieListAdapter(movieList)
        moviesRecyclerView.adapter = adapter
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    fun goToSearchActivity(view: View) = presenter.addMovie()


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

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