package com.erbe.mymvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erbe.mymvvm.R
import com.erbe.mymvvm.data.model.Movie
import com.erbe.mymvvm.data.net.RetrofitClient
import com.erbe.mymvvm.databinding.ItemMovieMainBinding
import com.erbe.mymvvm.setImageUrl
import java.util.*

class MovieListAdapter(private val movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieMainBinding>(
            layoutInflater,
            R.layout.item_movie_main,
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movie = movie
        if (movie.posterPath != null) {
            holder.binding.movieImageView.setImageUrl(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
        } else {
            holder.binding.movieImageView.setImageUrl(R.drawable.ic_local_movies_gray)
        }

        holder.binding.checkbox.isChecked = selectedMovies.contains(movie)

        holder.binding.checkbox.setOnCheckedChangeListener { checkbox, isChecked ->
            if (!selectedMovies.contains(movie) && isChecked) {
                selectedMovies.add(movies[position])
            } else {
                selectedMovies.remove(movies[position])
            }
        }
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieHolder(val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root)
}