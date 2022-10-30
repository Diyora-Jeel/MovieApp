package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.MovieDetailsRepositories
import com.example.moviePlex.viewmodel.MovieDetailsViewModel

class MovieDetailsViewModelFactory(private val repositories: MovieDetailsRepositories,private val movieId : Int) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(repositories,movieId) as T
    }

}