package com.example.curtmdb.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.curtmdb.R
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.ImageUtil
import kotlinx.android.synthetic.main.widget_popular_movie_item.view.*

class PopularMovieAdapter : PagedListAdapter<Movie, PopularMovieAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    var favoriteClickListener: ((pos: Int, movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.widget_popular_movie_item, parent, false)).apply {
            favorite.setOnClickListener {
                val pos = adapterPosition
                if (pos == NO_POSITION) {
                    return@setOnClickListener
                }
                val item = getItem(pos) ?: return@setOnClickListener

                favoriteClickListener?.invoke(pos, item)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle = payloads[0] as? Bundle ?: return
            if (bundle.containsKey(IS_FAVORITE)) {
                bindFavoriteIcon(holder, bundle.getBoolean(IS_FAVORITE))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position) ?: return

        with(holder) {
            val url = ImageUtil.getPopularMoviePosterUrl(itemView.context, movie)
            ImageUtil.load(url, poster, android.R.color.darker_gray)

            title.text = movie.title
            releaseDate.text = movie.releaseDate
            voteRate.text = movie.voteAverage.toString()
            bindFavoriteIcon(this, movie.isFavorite)
        }
    }

    private fun bindFavoriteIcon(holder: ViewHolder, isFavorite: Boolean) {
        holder.favorite.setImageResource(
            if (isFavorite) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
        )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.poster
        val title: TextView = itemView.title
        val releaseDate: TextView = itemView.release_date
        val voteRate: TextView = itemView.vote_rate
        val favorite: ImageView = itemView.favorite
    }

    companion object {
        private const val IS_FAVORITE = "is_favorite"

        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Movie, newItem: Movie): Any? {
                val bundle = Bundle()
                if (oldItem.isFavorite != newItem.isFavorite) {
                    bundle.putBoolean(IS_FAVORITE, newItem.isFavorite)
                }
                return if (bundle.isEmpty) {
                    super.getChangePayload(oldItem, newItem)
                } else {
                    return bundle
                }
            }
        }
    }
}