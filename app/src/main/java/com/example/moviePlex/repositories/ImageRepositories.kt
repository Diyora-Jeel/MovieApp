package com.example.moviePlex.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviePlex.R
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.image.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepositories(private val movieService: MovieService, context: Context)
{
    private val mutableLiveData = MutableLiveData<Image>()
    private val commonUtils = CommonUtils(context)

    val getImage : LiveData<Image> get() = mutableLiveData

    fun getImage(movieId : Int)
    {
        if(commonUtils.isNetworkAvailable) {
            val call = movieService.getImage(movieId, AppConstants.API_KEY)

            call.enqueue(object : Callback<Image> {
                override fun onResponse(
                    call: Call<Image>,
                    response: Response<Image>
                ) {
                    mutableLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<Image>, t: Throwable) {
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