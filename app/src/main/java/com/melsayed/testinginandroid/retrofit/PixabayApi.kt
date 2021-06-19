package com.melsayed.testinginandroid.retrofit

import com.melsayed.testinginandroid.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayApi {
    companion object {
        const val PIXABAY_KEY = BuildConfig.PIXABAY_KEY
        const val BASE_URL = "https://pixabay.com/"
    }
    @GET("api/?key=$PIXABAY_KEY")
    suspend fun searchPhotos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_Page: Int
    ) : PixabayResponse

}