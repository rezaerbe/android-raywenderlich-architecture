package com.erbe.myviper.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.erbe.myviper.R
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.data.net.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_search.view.*

class SearchListAdapter(
    private val movies: List<Movie>?,
    private var listener: (Movie?) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_search, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies?.size ?: 0

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies?.let {
            holder.bind(movies[position], position)
        }
    }

    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, position: Int) = with(view) {
            searchTitleTextView.text = movie.title
            searchReleaseDateTextView.text = movie.releaseDate
            view.setOnClickListener { listener(movies?.get(position)) }
            if (movie.posterPath != null)
                Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
                    .into(searchImageView)
            else {
                searchImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
        }
    }
}