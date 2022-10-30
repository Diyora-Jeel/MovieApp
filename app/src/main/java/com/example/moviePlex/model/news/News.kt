package com.example.moviePlex.model.news

data class News(
    val category: String,
    val data: ArrayList<Data>,
    val success: Boolean
)