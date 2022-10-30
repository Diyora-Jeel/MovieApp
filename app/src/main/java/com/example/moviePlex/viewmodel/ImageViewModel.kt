package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.image.Image
import com.example.moviePlex.repositories.ImageRepositories

class ImageViewModel(private val repositories: ImageRepositories, movieId : Int) : ViewModel()
{
    init {
        repositories.getImage(movieId)
    }

    val image : LiveData<Image>
        get() = repositories.getImage

}