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
import com.example.moviePlex.adapter.ImageAdapter
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.onClick.ImageVideoOnClick
import com.example.moviePlex.repositories.ImageRepositories
import com.example.moviePlex.viewmodel.ImageViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.ImageViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_details.bottomNavigationView
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity(), ImageVideoOnClick {

    private var movieId = 0
    private lateinit var imageViewModel: ImageViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        movieId = sharedPreferences.getInt(AppConstants.MOVIE_ID, 0)

        bottomNavigation()
        init()

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val imageRepositories = ImageRepositories(repositories, this)
        imageViewModel = ViewModelProvider(
            this,
            ImageViewModelFactory(imageRepositories, movieId)
        )[ImageViewModel::class.java]

        imageViewModel.image.observe(this, Observer {

            if (it.backdrops.isNotEmpty()) {
                recyclerView.layoutManager = LinearLayoutManager(this)
                imageAdapter = ImageAdapter(it.backdrops, this, this)
                recyclerView.adapter = imageAdapter
            } else {
                noImageTv.visibility = View.VISIBLE
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
        bottomNavigationView.setActiveNavigationIndex(3)
        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@ImageActivity, DetailsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this@ImageActivity, CastActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@ImageActivity, VideoActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }

    override fun onClickIV(id: String) {

        val intent = Intent(this, ImageDetailsActivity::class.java)
        intent.putExtra(AppConstants.IMAGE, id)
        startActivity(intent)
    }

}