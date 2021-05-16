package com.erbe.mymvvm.data

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erbe.mymvvm.data.db.MovieDao
import com.erbe.mymvvm.data.model.Movie
import com.erbe.mymvvm.data.model.MoviesResponse
import com.erbe.mymvvm.data.net.RetrofitClient
import com.erbe.mymvvm.db
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MovieRepositoryImpl : MovieRepository {

    private val movieDao: MovieDao = db.movieDao()
    private val retrofitClient = RetrofitClient()
    private val allMovies: LiveData<List<Movie>>

    init {
        allMovies = movieDao.getAll()
    }

    override fun deleteMovie(movie: Movie) {
        thread {
            db.movieDao().delete(movie.id)
        }
    }

    override fun getSavedMovies() = allMovies

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

    private class InsertAsyncTask internal constructor(private val dao: MovieDao) :
        AsyncTask<Movie, Void, Void>() {
        override fun doInBackground(vararg params: Movie): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: MovieDao) :
        AsyncTask<Boolean, Void, Void>() {
        override fun doInBackground(vararg p0: Boolean?): Void? {
            dao.deleteMovies(p0[0]!!)
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val dao: MovieDao) :
        AsyncTask<Movie, Void, Void>() {
        override fun doInBackground(vararg params: Movie): Void? {
            dao.updateMovie(params[0])
            return null
        }
    }
}