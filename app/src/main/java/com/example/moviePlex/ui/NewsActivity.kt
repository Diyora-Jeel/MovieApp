package com.example.moviePlex.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviePlex.R
import com.example.moviePlex.adapter.NewsAdapter
import com.example.moviePlex.api.APIClientNews
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.repositories.NewsRepositories
import com.example.moviePlex.viewmodel.NewsViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.NewsViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_news)

        bottomNavigation()
        init()

        val repositories = APIClientNews.getRetrofit().create(MovieService::class.java)
        val newsRepositories = NewsRepositories(repositories, this)
        newsViewModel = ViewModelProvider(this, NewsViewModelFactory(newsRepositories))[NewsViewModel::class.java]

        newsViewModel.news.observe(this, Observer {
            commonUtils.dismissCustomDialog(dialog)
            viewpager.adapter = NewsAdapter(this,it.data)
        })

    }

    private fun init() {

        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog, this)
    }

    private fun bottomNavigation() {
        bottomNavigationView.setActiveNavigationIndex(2)
        bottomNavigationView.setOnNavigationItemChangedListener(object : OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@NewsActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this@NewsActivity,SearchActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@NewsActivity,SettingActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                }
            }
        })
    }
}