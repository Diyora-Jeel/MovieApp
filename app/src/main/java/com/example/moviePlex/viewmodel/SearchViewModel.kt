package com.example.moviePlex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviePlex.model.movie.Movie
import com.example.moviePlex.repositories.SearchRepositories

class SearchViewModel(private val searchRepositories: SearchRepositories) : ViewModel() {


    init {
        searchRepositories.getPopular()
    }

    fun getSearch(query: String) {
        if (query == "") {
            searchRepositories.getPopular()
        } else {
            searchRepositories.getSearch(query)
        }
    }

    fun getPopular()
    {
        searchRepositories.getPopular()
    }

    val searchMovie: LiveData<Movie>
        get() = searchRepositories.getSearch

}