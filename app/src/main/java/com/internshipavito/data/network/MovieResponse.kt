package com.internshipavito.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val id: String,
    val name: String,
    val description: String,
    val rating: Rating,
    val poster: Poster,
    val year: String
)

@JsonClass(generateAdapter = true)
data class Poster(
    @Json(name = "previewUrl") val url: String
)

@JsonClass(generateAdapter = true)
data class Rating(
    val kp: String,
    val imdb: String,
    val filmCritics: String
)

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    @Json(name = "docs")val movies: List<GalleryItem>
)