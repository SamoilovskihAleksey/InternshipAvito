package com.internshipavito.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

class RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(MovieInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/v1.4/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun getApi(): KinopoiskApi {
        return retrofit.create(KinopoiskApi::class.java)
    }

    suspend fun fetchMovies(page: Int, limit: Int): List<GalleryItem> = getApi().fetchMovies(page, limit).movies

    suspend fun fetchMovie(@Path("movieId") movieId: String): GalleryItem = getApi().fetchMovie(movieId)

    suspend fun findMoviesById(@Path("movieName") movieName: String): List<GalleryItem> = getApi().findMovieById(movieName).movies

}