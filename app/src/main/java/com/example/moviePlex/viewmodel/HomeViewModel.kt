package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.movie.Movie
import com.example.moviePlex.repositories.HomeRepositories

class HomeViewModel(private val repositories: HomeRepositories) : ViewModel()
{
    init {
        repositories.getMovies()
    }

    val upComingMovie: LiveData<Movie>
        get() = repositories.getUpcoming

    val nowPlayingMovie: LiveData<Movie>
        get() = repositories.getNowPlayingMovie

    val popularMovie: LiveData<Movie>
        get() = repositories.getPopular

    val topRatedMovie: LiveData<Movie>
        get() = repositories.getTopRated
}
