package com.example.moviePlex.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviePlex.R
import com.example.moviePlex.adapter.CastAdapter
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.repositories.CastRepositories
import com.example.moviePlex.viewmodel.CastViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.CastViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_cast.*
import kotlinx.android.synthetic.main.activity_details.bottomNavigationView
import kotlinx.android.synthetic.main.activity_video.recyclerView

class CastActivity : AppCompatActivity() {

    private var movieId = 0
    private lateinit var castViewModel: CastViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var castAdapter: CastAdapter

    private lateinit var commonUtils : CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)

        bottomNavigation()
        init()

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        movieId = sharedPreferences.getInt(AppConstants.MOVIE_ID,0)

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val castRepositories = CastRepositories(repositories, this)
        castViewModel = ViewModelProvider(this, CastViewModelFactory(castRepositories, movieId))[CastViewModel::class.java]

        castViewModel.cast.observe(this, Observer {

            if(it.cast.isNotEmpty()) {
                recyclerView.layoutManager = LinearLayoutManager(this)
                castAdapter = CastAdapter(it.cast, this)
                recyclerView.adapter = castAdapter
            }
            else
            {
                noCastTv.visibility = View.VISIBLE
            }
            commonUtils.dismissCustomDialog(dialog)
        })

    }

    private fun init() {

        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog,this)
    }

    private fun bottomNavigation() {

        bottomNavigationView.setActiveNavigationIndex(1)

        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@CastActivity, DetailsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@CastActivity, VideoActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@CastActivity, ImageActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }
}