package com.erbe.mymvp.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erbe.mymvp.R
import com.erbe.mymvp.model.RemoteDataSource
import com.erbe.mymvp.model.TmdbResponse

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {
    private val TAG = "SearchActivity"

    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview)
        noMoviesTextView = findViewById(R.id.no_movies_textview)
        progressBar = findViewById(R.id.progress_bar)

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY).toString()

        setupViews()
        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        searchPresenter.getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupViews() {
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }

    override fun displayResult(tmdbResponse: TmdbResponse) {
        Log.d(TAG, "TmdbResponse results" + tmdbResponse.totalResults)

        progressBar.visibility = INVISIBLE
        if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
            searchResultsRecyclerView.visibility = INVISIBLE
            noMoviesTextView.visibility = VISIBLE
        } else {
            adapter = SearchAdapter(
                tmdbResponse.results
                    ?: arrayListOf(), this@SearchActivity, itemListener
            )
            searchResultsRecyclerView.adapter = adapter

            searchResultsRecyclerView.visibility = VISIBLE
            noMoviesTextView.visibility = INVISIBLE
        }
    }

    override fun showToast(string: String) {
        Toast.makeText(this@SearchActivity, string, Toast.LENGTH_LONG).show()
    }

    override fun displayError(string: String) {
        showToast(string)
    }

    companion object {

        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)

            val replyIntent = Intent()
            replyIntent.putExtra(SearchActivity.EXTRA_TITLE, movie.title)
            replyIntent.putExtra(SearchActivity.EXTRA_RELEASE_DATE, movie.releaseDate)
            replyIntent.putExtra(SearchActivity.EXTRA_POSTER_PATH, movie.posterPath)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(view: View, position: Int)
    }
}