package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.news.News
import com.example.moviePlex.repositories.NewsRepositories

class NewsViewModel(private val repositories: NewsRepositories) : ViewModel() {

    init {
        repositories.getNews()
    }

    val news: LiveData<News>
        get() = repositories.getNews

}