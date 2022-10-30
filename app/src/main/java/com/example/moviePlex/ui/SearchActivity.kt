package com.example.moviePlex.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviePlex.R
import com.example.moviePlex.adapter.SearchAdapter
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.model.movie.Result
import com.example.moviePlex.onClick.MainOnclick
import com.example.moviePlex.repositories.SearchRepositories
import com.example.moviePlex.viewmodel.SearchViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.SearchViewModelFactory
import com.sdsmdg.tastytoast.TastyToast
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity(),MainOnclick {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchList: ArrayList<Result>

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    lateinit var sharedPreferences : SharedPreferences
    private lateinit var myEdit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        bottomNavigation()
        init()

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val searchRepositories = SearchRepositories(repositories, this)
        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(searchRepositories))[SearchViewModel::class.java]

        searchBar.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.getSearch(searchBar.text.toString())
                commonUtils.hideKeyboard(this)
                commonUtils.showCustomDialog(dialog, this)
                true
            } else false
        })

        searchViewModel.searchMovie.observe(this, Observer {
            if (it.results.size != 0) {
                searchRecyclerView.layoutManager = LinearLayoutManager(this)
                searchAdapter = SearchAdapter(this, it.results,this)
                searchRecyclerView.adapter = searchAdapter
                commonUtils.dismissCustomDialog(dialog)
            }
            else
            {
                searchViewModel.getPopular()
                TastyToast.makeText(this, "No data Found", TastyToast.LENGTH_LONG, TastyToast.INFO)
            }
        })
    }

    private fun init() {

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, Context.MODE_PRIVATE)
        myEdit = sharedPreferences.edit()

        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog, this)
        searchList = ArrayList()
    }

    private fun bottomNavigation() {
        bottomNavigationView.setActiveNavigationIndex(1)
        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@SearchActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@SearchActivity, NewsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@SearchActivity, SettingActivity::class.java)
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