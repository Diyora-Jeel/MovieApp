package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.video.Video
import com.example.moviePlex.repositories.VideoRepositories

class VideoViewModel(private val repositories: VideoRepositories, movieId : Int) : ViewModel()
{
    init {
        repositories.getVideo(movieId)
    }

    val video : LiveData<Video>
        get() = repositories.getVideo

}