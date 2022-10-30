package com.example.moviePlex.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviePlex.R
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.movie.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepositories(private val movieService: MovieService, context: Context) {

    private val searchMutableLiveData = MutableLiveData<Movie>()
    private val commonUtils = CommonUtils(context)

    val getSearch: LiveData<Movie>
        get() = searchMutableLiveData

    fun getSearch(query: String) {
        if (commonUtils.isNetworkAvailable) {
            val call =
                movieService.getSearch(AppConstants.API_KEY, query, AppConstants.INCLUDE_ADULT)

            call.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    searchMutableLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    commonUtils.createDialog(R.drawable.ic_error,"Network Issue ","Please try again")
                }
            })
        } else {
            commonUtils.createDialog(R.drawable.ic_no_internet,"No Internet","Please check your connection status and try again")
        }
    }

    fun getPopular()
    {
        if(commonUtils.isNetworkAvailable) {
            val call = movieService.getPopular(AppConstants.API_KEY)

            call.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    searchMutableLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    commonUtils.createDialog(R.drawable.ic_error,"Network Issue ","Please try again")
                }
            })
        }
        else
        {
            commonUtils.createDialog(R.drawable.ic_no_internet,"No Internet","Please check your connection status and try again")
        }
    }
}