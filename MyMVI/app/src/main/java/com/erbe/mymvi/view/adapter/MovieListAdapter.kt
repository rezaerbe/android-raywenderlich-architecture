package com.erbe.mymvi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.erbe.mymvi.R
import com.erbe.mymvi.data.model.Movie
import com.erbe.mymvi.data.net.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_main.view.*

class MovieListAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_main, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) =
        holder.bind(movies[position])

    fun setMovies(movieList: List<Movie>) {
        movies = movieList
        notifyDataSetChanged()
    }

    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(view) {
            movieTitleTextView.text = movie.title
            movieReleaseDateTextView.text = movie.releaseDate
            if (movie.posterPath != null)
                Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
                    .into(movieImageView)
            else {
                movieImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
        }
    }

    fun getMoviesAtPosition(position: Int) = movies[position]
}