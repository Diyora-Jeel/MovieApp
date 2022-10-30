package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.CastRepositories
import com.example.moviePlex.viewmodel.CastViewModel

class CastViewModelFactory(private val repositories: CastRepositories, private val movieId : Int)  : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CastViewModel(repositories,movieId) as T
    }

}