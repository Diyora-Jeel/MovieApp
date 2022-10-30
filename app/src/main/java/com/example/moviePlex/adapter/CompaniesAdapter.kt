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
import com.example.moviePlex.model.moviedetails.ProductionCompany
import java.util.*
import kotlin.collections.ArrayList

class CompaniesAdapter(private val context: Context, private val list : ArrayList<ProductionCompany>) : RecyclerView.Adapter<CompaniesAdapter.NowPlayingViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.companies_item,parent,false)
        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val item = list[position]
        Glide.with(context).load(AppConstants.IMAGE_URL +item.logo_path).placeholder(R.drawable.ic_no_image).into(holder.imageView)
        holder.nameTv.text = item.name
        val loc = Locale(item.origin_country)
        holder.countryTv.text = loc.getDisplayLanguage(loc)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NowPlayingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val nameTv : TextView = itemView.findViewById(R.id.nameTv)
        val countryTv : TextView = itemView.findViewById(R.id.countryTv)
    }

}