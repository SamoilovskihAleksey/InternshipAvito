package com.internshipavito.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("movie?")
    suspend fun fetchMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): MoviesResponse

    @GET("movie/{movieId}")
    suspend fun fetchMovie(
        @Path("movieId") movieId: String
    ): GalleryItem

    @GET("movie/search")
    suspend fun findMovieById(
        @Query("query") name: String
    ): MoviesResponse
}
