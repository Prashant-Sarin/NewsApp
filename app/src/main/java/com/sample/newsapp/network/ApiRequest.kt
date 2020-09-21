package com.sample.newsapp.network

import android.util.Log
import okio.IOException
import retrofit2.Response

abstract class ApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            Log.d("API Response", response.body().toString())
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            Log.e("API Error", error.toString())
            throw IOException(error.toString())
        }
    }
}