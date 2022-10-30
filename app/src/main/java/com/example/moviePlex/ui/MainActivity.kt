package com.example.moviePlex.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviePlex.R
import com.example.moviePlex.adapter.*
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.movie.Result
import com.example.moviePlex.onClick.MainOnclick
import com.example.moviePlex.repositories.HomeRepositories
import com.example.moviePlex.viewmodel.HomeViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.HomeViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , MainOnclick {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var playingNowAdapter: PlayingNowAdapter
    private lateinit var playingNowList: ArrayList<Result>

    private lateinit var upComingAdapter: UpComingAdapter
    private lateinit var upComingList: ArrayList<Result>

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var popularList: ArrayList<Result>

    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var topRatedList: ArrayList<Result>

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    lateinit var sharedPreferences : SharedPreferences
    lateinit var myEdit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation()
        init()

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val homeRepositories = HomeRepositories(repositories, this)

        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(homeRepositories)
        )[HomeViewModel::class.java]

        homeViewModel.nowPlayingMovie.observe(this, Observer {

            for (i in 0 until it.results.size) {
                if (!it.results[i].adult) {
                    playingNowList.add(it.results[i])
                }
            }

            playNowRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            playingNowAdapter = PlayingNowAdapter(this, playingNowList,this)
            playNowRecyclerView.adapter = playingNowAdapter

        })

        homeViewModel.upComingMovie.observe(this, Observer {
            upComingRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            for (i in 0 until it.results.size) {
                if (!it.results[i].adult) {
                    upComingList.add(it.results[i])
                }
            }

            upComingAdapter = UpComingAdapter(this, it.results,this)
            upComingRecyclerView.adapter = upComingAdapter

            commonUtils.dismissCustomDialog(dialog)

        })

        homeViewModel.popularMovie.observe(this, Observer {
            popularRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            for (i in 0 until it.results.size) {
                if (!it.results[i].adult) {
                    popularList.add(it.results[i])
                }
            }
            popularAdapter = PopularAdapter(this, it.results,this)
            popularRecyclerView.adapter = popularAdapter
        })

        homeViewModel.topRatedMovie.observe(this, Observer {
            topRatedRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            for (i in 0 until it.results.size) {
                if (!it.results[i].adult) {
                    topRatedList.add(it.results[i])
                }
            }

            topRatedAdapter = TopRatedAdapter(this, it.results,this)
            topRatedRecyclerView.adapter = topRatedAdapter
        })

    }

    private fun init() {

        playingNowList = ArrayList()
        upComingList = ArrayList()
        popularList = ArrayList()
        topRatedList = ArrayList()

        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog, this)

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        myEdit = sharedPreferences.edit()

    }

    private fun bottomNavigation() {

        bottomNavigationView.setActiveNavigationIndex(0)

        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    1 -> {
                        val intent = Intent(this@MainActivity, SearchActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@MainActivity, NewsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }

    override fun onClick(movieId: Int) {

        myEdit.putInt(AppConstants.MOVIE_ID, movieId)
        myEdit.commit()

        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}