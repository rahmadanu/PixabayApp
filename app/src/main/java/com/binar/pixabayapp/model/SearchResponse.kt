package com.binar.pixabayapp.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalHits")
    val totalHits: Int?,
    @SerializedName("hits")
    val posts: List<Post>?
)
