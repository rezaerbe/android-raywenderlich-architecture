package com.erbe.mymvp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null,

    @SerializedName("video")
    @Expose
    var video: Boolean? = null,

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null,

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,

    @SerializedName("genre_ids")
    @Expose
    @TypeConverters(IntegerListTypeConverter::class)
    var genreIds: List<Int>? = null,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null,

    @SerializedName("overview")
    @Expose
    var overview: String? = null,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
) {

    /**
     * Constructor for manually added movies
     *
     */
    constructor(title: String, releaseDate: String, posterPath: String) : this() {
        this.title = title
        this.releaseDate = releaseDate
        this.posterPath = posterPath
    }

    /**
     *
     * @param genreIds
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */
    constructor(
        voteCount: Int?,
        video: Boolean?,
        voteAverage: Float?,
        title: String,
        popularity: Float?,
        posterPath: String,
        originalLanguage: String,
        originalTitle: String,
        genreIds: List<Int>,
        backdropPath: String,
        adult: Boolean?,
        overview: String,
        releaseDate: String
    ) : this() {
        this.voteCount = voteCount
        this.video = video
        this.voteAverage = voteAverage
        this.title = title
        this.popularity = popularity
        this.posterPath = posterPath
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.genreIds = genreIds
        this.backdropPath = backdropPath
        this.adult = adult
        this.overview = overview
        this.releaseDate = releaseDate
    }
}