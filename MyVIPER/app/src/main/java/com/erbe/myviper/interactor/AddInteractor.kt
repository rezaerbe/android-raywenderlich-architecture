package com.erbe.myviper.interactor

import com.erbe.myviper.AddContract
import com.erbe.myviper.data.MovieRepositoryImpl
import com.erbe.myviper.data.entity.Movie

class AddInteractor : AddContract.Interactor {

    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    override fun addMovie(movie: Movie) = repository.saveMovie(movie)
}