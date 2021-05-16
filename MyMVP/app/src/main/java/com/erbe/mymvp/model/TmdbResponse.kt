package com.erbe.mymvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse {

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    constructor(page: Int?, totalResults: Int?, totalPages: Int?, results: List<Movie>) : super() {
        this.page = page
        this.totalResults = totalResults
        this.totalPages = totalPages
        this.results = results
    }
}