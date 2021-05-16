package com.erbe.mymvp.search

import com.erbe.mymvp.model.TmdbResponse

class SearchContract {

    interface PresenterInterface {

        fun getSearchResults(query: String)
        fun stop()
    }

    interface ViewInterface {

        fun showToast(string: String)
        fun displayResult(tmdbResponse: TmdbResponse)
        fun displayError(string: String)
    }
}