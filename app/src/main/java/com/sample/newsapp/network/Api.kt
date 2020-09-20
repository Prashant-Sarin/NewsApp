package com.sample.newsapp.network

import com.google.gson.GsonBuilder
import com.sample.newsapp.model.NewsModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    companion object {
        fun create(): Api {

            val gson = GsonBuilder()
                .create()

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return retrofit.create(Api::class.java)
        }

    }


    @GET("top-headlines")
    fun getHeadlines(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<NewsModel>

}
