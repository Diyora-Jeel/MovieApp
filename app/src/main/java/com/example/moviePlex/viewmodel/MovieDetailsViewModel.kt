package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.moviedetails.MovieDetails
import com.example.moviePlex.repositories.MovieDetailsRepositories

class MovieDetailsViewModel(private val repositories: MovieDetailsRepositories,movieId : Int) : ViewModel() {

    init {
        repositories.getMovieDetails(movieId)
    }

    val movieDetailsInfo: LiveData<MovieDetails>
        get() = repositories.getMovieDetails

}