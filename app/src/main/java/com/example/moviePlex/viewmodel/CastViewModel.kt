package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.cast.Cast
import com.example.moviePlex.repositories.CastRepositories

class CastViewModel(private val repositories: CastRepositories, movieId : Int) : ViewModel()
{
    init {
        repositories.getCast(movieId)
    }

    val cast : LiveData<Cast>
        get() = repositories.getCast
}