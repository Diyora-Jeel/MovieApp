package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.ImageRepositories
import com.example.moviePlex.viewmodel.ImageViewModel

class ImageViewModelFactory(private val repositories: ImageRepositories, private val movieId : Int)  : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageViewModel(repositories,movieId) as T
    }

}