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

class HomeRepositories(private val movieService: MovieService, context: Context)
{
    private val commonUtils = CommonUtils(context)

    private val upComingLiveData = MutableLiveData<Movie>()
    private val nowPlayingLiveData = MutableLiveData<Movie>()
    private val popularLiveData = MutableLiveData<Movie>()
    private val topRatedLiveData = MutableLiveData<Movie>()

    val getUpcoming : LiveData<Movie>
        get() = upComingLiveData

    val getNowPlayingMovie : LiveData<Movie>
        get() = nowPlayingLiveData

    val getPopular : LiveData<Movie>
        get() = popularLiveData

    val getTopRated : LiveData<Movie>
        get() = topRatedLiveData


    fun getMovies()
    {
        if(commonUtils.isNetworkAvailable) {

//            upComingLiveData
            val upComingCall = movieService.getUpcoming(AppConstants.API_KEY)

            upComingCall.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    upComingLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    commonUtils.createDialog(R.drawable.ic_error,"Network Issue ","Please try again")
                }
            })

//            nowPlayingLiveData
            val nowPlayingCall = movieService.getNowPlaying(AppConstants.API_KEY)

            nowPlayingCall.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    nowPlayingLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    commonUtils.createDialog(R.drawable.ic_error,"Network Issue ","Please try again")
                }
            })

//            popularLiveData
            val popularCall = movieService.getPopular(AppConstants.API_KEY)

            popularCall.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    popularLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    commonUtils.createDialog(R.drawable.ic_error,"Network Issue ","Please try again")
                }
            })


//            topRatedLiveData
            val topRatedCall = movieService.getTopRated(AppConstants.API_KEY)

            topRatedCall.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    topRatedLiveData.postValue(response.body())
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