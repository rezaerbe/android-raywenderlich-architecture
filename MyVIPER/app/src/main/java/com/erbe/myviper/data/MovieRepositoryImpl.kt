package com.erbe.myviper.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erbe.myviper.data.db.MovieDao
import com.erbe.myviper.data.entity.Movie
import com.erbe.myviper.data.entity.MoviesResponse
import com.erbe.myviper.data.net.RetrofitClient
import com.erbe.myviper.db
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MovieRepositoryImpl : MovieRepository {

    private val movieDao: MovieDao
    private val retrofitClient = RetrofitClient()

    init {
        val movieDatabase = db
        movieDao = movieDatabase.movieDao()
    }

    override fun deleteMovie(movie: Movie) {
        thread {
            db.movieDao().delete(movie.id)
        }
    }

    override fun getSavedMovies(): LiveData<List<Movie>> {
        return movieDao.getAll()
    }

    override fun saveMovie(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    override fun searchMovies(query: String): LiveData<List<Movie>?> {

        val data = MutableLiveData<List<Movie>>()

        retrofitClient.searchMovies(query).enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                data.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                data.value = response.body()?.results
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.results}")
            }
        })
        return data
    }
}