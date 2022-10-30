package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.NewsRepositories
import com.example.moviePlex.viewmodel.NewsViewModel

class NewsViewModelFactory(private val repositories: NewsRepositories) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repositories) as T
    }

}