package com.heroapps.codechallenge4.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.heroapps.codechallenge4.R
import com.heroapps.codechallenge4.common.util.getDrawableFromString
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.data.source.remote.response.MoviesResponse
import com.heroapps.codechallenge4.databinding.MovieItemBinding

class MovieAdapter(private val context: Context, private var movies: MoviesResponse, private val listener: MovieAdapterListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface MovieAdapterListener {
        fun onItemClick(el: MovieItemResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with (holder){

        val imageViewMovie = itemView.findViewById<ImageView>(R.id.imageView_movie)
        val textViewTitle = itemView.findViewById<TextView>(R.id.textView_title)
        val textViewSubtitle = itemView.findViewById<TextView>(R.id.textView_subtitle)
        val textViewWatchlist = itemView.findViewById<TextView>(R.id.textView_watchlist)

        val item = movies.items[position]

        bind(movies.items[position])

        itemView.setOnClickListener { listener.onItemClick(item) }
        imageViewMovie.setImageResource(context.getDrawableFromString(item.imageName))
        textViewTitle.text = item.title.toString()
        textViewSubtitle.text = context.resources.getString(R.string.subtitle, item.duration, item.genre)

        if(item.isOnWatchlist == true) {
            textViewWatchlist.visibility = View.VISIBLE
        } else {
            textViewWatchlist.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount() = movies.items.size

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieItemResponse) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }

}