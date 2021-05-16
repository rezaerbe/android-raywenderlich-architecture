package com.erbe.mymvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.mymvc.model.Movie
import com.erbe.mymvc.network.RetrofitClient
import com.squareup.picasso.Picasso
import java.util.*

class MainAdapter(internal var movieList: List<Movie>, internal var context: Context) :
    RecyclerView.Adapter<MainAdapter.MoviesHolder>() {
    // HashMap to keep track of which items were selected for deletion
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].releaseDate
        if (movieList[position].posterPath.equals("")) {
            holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath)
                .into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var titleTextView: TextView
        internal var releaseDateTextView: TextView
        internal var movieImageView: ImageView
        internal var checkBox: CheckBox

        init {
            titleTextView = v.findViewById(R.id.title_textview)
            releaseDateTextView = v.findViewById(R.id.release_date_textview)
            movieImageView = v.findViewById(R.id.movie_imageview)
            checkBox = v.findViewById(R.id.checkbox)
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movieList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movieList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(movieList[adapterPosition])
                }
            }
        }
    }
}