package com.example.moviePlex.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviePlex.R
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.news.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepositories(private val movieService: MovieService, context: Context)
{
    private val mutableLiveData = MutableLiveData<News>()
    private val commonUtils = CommonUtils(context)

    val getNews : LiveData<News>
    get() = mutableLiveData

    fun getNews()
    {
        if(commonUtils.isNetworkAvailable) {
            val call = movieService.getNews(AppConstants.CATEGORY)

            call.enqueue(object : Callback<News> {
                override fun onResponse(
                    call: Call<News>,
                    response: Response<News>
                ) {
                    mutableLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
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