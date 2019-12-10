package com.example.curtmdb.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies", indices = [Index(value = ["id", "title"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true) val primaryKey: Int,
    val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("release_date") val releaseDate: String,
    val popularity: Float,
    val page: Int = 1,
    val isFavorite: Boolean = false
)