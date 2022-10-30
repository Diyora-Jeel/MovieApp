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
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.model.video.Result
import com.example.moviePlex.onClick.ImageVideoOnClick

class VideoAdapter(
    private val list: ArrayList<Result>,
    val context: Context,
    private val imageVideoOnClick: ImageVideoOnClick
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        val item = list[position]

        Glide.with(context).load(AppConstants.VIDEO_IMAGE + item.key + "/0.jpg")
            .placeholder(R.drawable.ic_no_image).into(holder.imageView)
        holder.title.text = item.name
        holder.type.text = item.type

        holder.cardView.setOnClickListener {
            imageVideoOnClick.onClickIV(item.key)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val title: TextView = itemView.findViewById(R.id.title)
        val type: TextView = itemView.findViewById(R.id.type)
    }
}