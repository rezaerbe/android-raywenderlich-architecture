package com.erbe.mymvi.domain

import com.erbe.mymvi.data.model.Movie

sealed class MovieState {
    object LoadingState : MovieState()
    data class DataState(val data: List<Movie>) : MovieState()
    data class ErrorState(val data: String) : MovieState()
    data class ConfirmationState(val movie: Movie) : MovieState()
    object FinishState : MovieState()
}