package com.example.moviePlex.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviePlex.R
import com.example.moviePlex.adapter.VideoAdapter
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.onClick.ImageVideoOnClick
import com.example.moviePlex.repositories.VideoRepositories
import com.example.moviePlex.viewmodel.VideoViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.VideoViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_details.bottomNavigationView
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity(), ImageVideoOnClick {

    private var movieId = 0
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var videoAdapter: VideoAdapter

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        bottomNavigation()
        init()

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        movieId = sharedPreferences.getInt(AppConstants.MOVIE_ID, 0)

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val videoRepositories = VideoRepositories(repositories, this)
        videoViewModel = ViewModelProvider(
            this,
            VideoViewModelFactory(videoRepositories, movieId)
        )[VideoViewModel::class.java]

        videoViewModel.video.observe(this, Observer {

            if (it.results.isNotEmpty()) {
                recyclerView.layoutManager = LinearLayoutManager(this)
                videoAdapter = VideoAdapter(it.results, this, this)
                recyclerView.adapter = videoAdapter
            } else {
                noVideoTv.visibility = View.VISIBLE
            }
            commonUtils.dismissCustomDialog(dialog)
        })

    }

    private fun init() {
        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog, this)
    }

    private fun bottomNavigation() {
        bottomNavigationView.setActiveNavigationIndex(2)
        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@VideoActivity, DetailsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this@VideoActivity, CastActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@VideoActivity, ImageActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }

    override fun onClickIV(id: String) {

        val intent = Intent(this, VideoDetailsActivity::class.java)
        intent.putExtra(AppConstants.VIDEO, id)
        startActivity(intent)
    }
}