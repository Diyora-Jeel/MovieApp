package com.example.moviePlex.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviePlex.R
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.video.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepositories(private val movieService: MovieService, context: Context)
{
    private val mutableLiveData = MutableLiveData<Video>()
    private val commonUtils = CommonUtils(context)

    val getVideo : LiveData<Video> get() = mutableLiveData

    fun getVideo(movieId : Int)
    {
        if(commonUtils.isNetworkAvailable) {
            val call = movieService.getVideo(movieId,AppConstants.API_KEY)

            call.enqueue(object : Callback<Video> {
                override fun onResponse(
                    call: Call<Video>,
                    response: Response<Video>
                ) {
                    mutableLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Video>, t: Throwable) {
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