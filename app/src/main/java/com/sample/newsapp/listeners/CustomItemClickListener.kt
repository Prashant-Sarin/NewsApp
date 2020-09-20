package com.sample.newsapp.listeners

import android.view.View

interface CustomItemClickListener {
    fun <T> onItemClick(view: View, t: T)
}