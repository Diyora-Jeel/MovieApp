package com.example.moviePlex.viewmodel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.repositories.VideoRepositories
import com.example.moviePlex.viewmodel.VideoViewModel

class VideoViewModelFactory(private val repositories: VideoRepositories, private val movieId : Int)  : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoViewModel(repositories,movieId) as T
    }

}