package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.HomeRepositories
import com.example.moviePlex.viewmodel.HomeViewModel

class HomeViewModelFactory(private val repositories: HomeRepositories) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repositories) as T
    }
}