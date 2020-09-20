package com.sample.newsapp.model

import com.google.gson.annotations.SerializedName

data class SourceModel (
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)