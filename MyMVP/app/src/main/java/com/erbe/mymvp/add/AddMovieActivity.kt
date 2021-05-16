package com.erbe.mymvp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erbe.mymvp.R
import com.erbe.mymvp.model.LocalDataSource
import com.erbe.mymvp.network.RetrofitClient.TMDB_IMAGEURL
import com.erbe.mymvp.search.SearchActivity
import com.squareup.picasso.Picasso

class AddMovieActivity : AppCompatActivity(), AddMovieContract.ViewInterface {
    private lateinit var titleEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var movieImageView: ImageView
    private lateinit var addMoviePresenter: AddMovieContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        setupPresenter()
        setupViews()
    }

    fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        addMoviePresenter = AddMoviePresenter(this, dataSource)
    }

    fun setupViews() {
        titleEditText = findViewById(R.id.movie_title)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)
    }

    //search onClick
    fun goToSearchMovieActivity(v: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    //addMovie onClick
    fun onClickAddMovie(view: View) {
        val title = titleEditText.text.toString()
        val releaseDate = releaseDateEditText.text.toString()
        val posterPath = if (movieImageView.tag != null) movieImageView.tag.toString() else ""

        addMoviePresenter.addMovie(title, releaseDate, posterPath)

    }

    override fun returnToMain() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddMovieActivity.runOnUiThread {
            titleEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
            releaseDateEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE))
            movieImageView.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)
            Picasso.get()
                .load(TMDB_IMAGEURL + data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH))
                .into(movieImageView)
        }
    }

    override fun showToast(string: String) {
        Toast.makeText(this@AddMovieActivity, string, Toast.LENGTH_LONG).show()
    }

    override fun displayError(string: String) {
        showToast(string)
    }

    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}