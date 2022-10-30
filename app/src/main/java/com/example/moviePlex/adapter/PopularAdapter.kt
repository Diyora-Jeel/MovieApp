package com.example.moviePlex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviePlex.R
import com.example.moviePlex.constants.AppConstants.Companion.IMAGE_URL
import com.example.moviePlex.model.movie.Result
import com.example.moviePlex.onClick.MainOnclick

class PopularAdapter(private val context: Context, private val list : ArrayList<Result>,private val mainOnclick: MainOnclick) : RecyclerView.Adapter<PopularAdapter.NowPlayingViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false)
        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val item = list[position]

        Glide.with(context).load(IMAGE_URL+item.poster_path).placeholder(R.drawable.ic_no_image).into(holder.imageView)
        holder.title.text = item.title
        holder.rating.text = item.vote_average.toString()

        holder.cardView.setOnClickListener {
            mainOnclick.onClick(item.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NowPlayingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val cardView : CardView = itemView.findViewById(R.id.cardView)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val title : TextView = itemView.findViewById(R.id.title)
        val rating : TextView = itemView.findViewById(R.id.rating)
    }

}