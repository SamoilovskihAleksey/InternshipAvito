package com.internshipavito.data.network

import okhttp3.Interceptor
import okhttp3.Response

class MovieInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("X-API-KEY", API_KEY)
            .build()

        return chain.proceed(request)
    }

    private companion object {

        private const val API_KEY = "indicate_your_api_key_here"
    }

}