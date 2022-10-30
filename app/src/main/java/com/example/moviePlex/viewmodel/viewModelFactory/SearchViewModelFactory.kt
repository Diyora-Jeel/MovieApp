package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.SearchRepositories
import com.example.moviePlex.viewmodel.SearchViewModel

class SearchViewModelFactory(private val repositories: SearchRepositories) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repositories) as T
    }
}