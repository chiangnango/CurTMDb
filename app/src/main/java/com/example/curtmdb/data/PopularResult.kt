package com.example.curtmdb.data

import com.google.gson.annotations.SerializedName

data class PopularResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("results")
    val movies: List<Movie>
)