package com.example.curtmdb.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curtmdb.R
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.ImageUtil
import kotlinx.android.synthetic.main.widget_popular_movie_item.view.*

class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    var data: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.widget_popular_movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = data[position]

        with(holder) {
            val url = ImageUtil.getPopularMoviePosterUrl(itemView.context, movie)
            ImageUtil.load(url, poster, android.R.color.darker_gray)

            title.text = movie.title
            releaseDate.text = movie.releaseDate
            voteRate.text = movie.voteAverage.toString()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.poster
        val title: TextView = itemView.title
        val releaseDate: TextView = itemView.release_date
        val voteRate: TextView = itemView.vote_rate
    }
}