package com.sample.newsapp.listeners

interface RestCallback<T> {
    fun onSuccess(t:T?)
    fun onFailure(error: String)
}