package com.example.curtmdb.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.curtmdb.R
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.ImageUtil
import kotlinx.android.synthetic.main.widget_popular_movie_item.view.*

class PopularMovieAdapter : PagedListAdapter<Movie, PopularMovieAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    var data: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.widget_popular_movie_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position) ?: return

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

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}