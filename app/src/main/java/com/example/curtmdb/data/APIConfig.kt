package com.example.curtmdb.data

import com.google.gson.annotations.SerializedName

data class APIConfig(@SerializedName("images") val imageConfig: List<ImageConfig>)

data class ImageConfig(
    @SerializedName("secure_base_url") val baseUrl: String,
    @SerializedName("poster_sizes") val posterSizes: List<String>
)