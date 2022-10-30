package com.example.moviePlex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviePlex.R
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.model.cast.CastX

class CastAdapter(private val list: ArrayList<CastX>, val context: Context) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cast_item, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

        val item = list[position]

        Glide.with(context).load(AppConstants.IMAGE_URL + item.profile_path).placeholder(R.drawable.ic_no_image).into(holder.imageView)
        holder.character.text = item.character
        holder.name.text = item.name
        holder.department.text = item.known_for_department
        holder.popularity.text = item.popularity.toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.name)
        val character: TextView = itemView.findViewById(R.id.character)
        val department: TextView = itemView.findViewById(R.id.department)
        val popularity: TextView = itemView.findViewById(R.id.popularity)
    }
}