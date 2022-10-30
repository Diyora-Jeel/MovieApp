package com.example.moviePlex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviePlex.R
import com.example.moviePlex.model.news.Data
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val context: Context, private val list : ArrayList<Data>) : RecyclerView.Adapter<NewsAdapter.NowPlayingViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val item = list[position]

        Glide.with(context).load(item.imageUrl).placeholder(R.drawable.ic_no_image).into(holder.imageView)
        holder.title.text = item.title
        holder.body.text = item.content
        Log.d("TAG", "onCreate: "+item.title)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NowPlayingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val title : TextView = itemView.findViewById(R.id.title)
        val body : TextView = itemView.findViewById(R.id.body)
    }
}