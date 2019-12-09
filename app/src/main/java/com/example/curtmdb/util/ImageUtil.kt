package com.example.curtmdb.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.curtmdb.R
import com.example.curtmdb.data.ImageConfig
import com.example.curtmdb.data.Movie

object ImageUtil {

    private const val ORIGINAL = "original"

    lateinit var imageConfig: ImageConfig

    fun isImageConfigReady(): Boolean {
        return ::imageConfig.isInitialized
    }

    fun getPopularMoviePosterUrl(context: Context, movie: Movie): String {
        if (!::imageConfig.isInitialized) {
            return ""
        }

        val width = context.resources.getDimensionPixelOffset(R.dimen.popular_movie_poster_width)
        return "${imageConfig.baseUrl}${getSizePath(width, imageConfig.posterSizes)}${movie.posterPath}"
    }

    // TODO: add unit test
    private fun getSizePath(desiredWidth: Int, availableSizeList: List<String>): String {
        for (value: String in availableSizeList) {
            if (value == ORIGINAL) {
                return ORIGINAL
            }

            value.substring(1).toIntOrNull()?.let {
                if (it >= desiredWidth) {
                    return value
                }
            }
        }
        return ORIGINAL
    }

    fun load(url: String, target: ImageView, @DrawableRes placeholderResId: Int? = null) {
        Glide.with(target.context).load(url).apply {
            if (placeholderResId != null) {
                placeholder(placeholderResId)
            }
        }.into(target)
    }
}