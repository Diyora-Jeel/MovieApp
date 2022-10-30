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
import com.bumptech.glide.Glide
import com.example.moviePlex.R
import com.example.moviePlex.adapter.CompaniesAdapter
import com.example.moviePlex.api.APIClientMovie
import com.example.moviePlex.api.MovieService
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.repositories.CastRepositories
import com.example.moviePlex.repositories.MovieDetailsRepositories
import com.example.moviePlex.viewmodel.CastViewModel
import com.example.moviePlex.viewmodel.MovieDetailsViewModel
import com.example.moviePlex.viewmodel.viewModelFactory.CastViewModelFactory
import com.example.moviePlex.viewmodel.viewModelFactory.MovieDetailsViewModelFactory
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.bottomNavigationView
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class DetailsActivity : AppCompatActivity() {

    private var movieId = 0

    private lateinit var castViewModel: CastViewModel
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var companiesAdapter: CompaniesAdapter

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        bottomNavigation()
        init()

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        movieId = sharedPreferences.getInt(AppConstants.MOVIE_ID,0)

        val repositories = APIClientMovie.getRetrofit().create(MovieService::class.java)
        val movieDetailsRepositories = MovieDetailsRepositories(repositories, this)

        val castRepositories = CastRepositories(repositories, this)
        castViewModel = ViewModelProvider(this, CastViewModelFactory(castRepositories, movieId))[CastViewModel::class.java]

        movieDetailsViewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(movieDetailsRepositories, movieId)
        )[MovieDetailsViewModel::class.java]

        castViewModel.cast.observe(this, Observer {

            for (i in 0 until it.crew.size)
            {
                if(it.crew[i].job == "Director")
                {
                    directorTv.text = it.crew[i].name
                }

                if(it.crew[i].job == "Producer")
                {
                    producerTv.text = it.crew[i].name
                }
            }

        })

        movieDetailsViewModel.movieDetailsInfo.observe(this, Observer {

            Glide.with(this).load(AppConstants.IMAGE_URL_ORIGINAL + it.backdrop_path)
                .placeholder(R.drawable.ic_no_image).into(bannerImageView)
            Glide.with(this).load(AppConstants.IMAGE_URL + it.poster_path)
                .placeholder(R.drawable.ic_no_image).into(postImageView)
            titleToolbar.title = it.title
            titleTv.text = it.title
            val loc = Locale(it.original_language)
            languageTv.text = loc.getDisplayLanguage(loc)
            ratingStart.rating = it.vote_average.toFloat()
            dateTv.text = it.release_date
            popularityTv.text = it.popularity.toString()
            contentTv.text = it.overview
            budgetTv.text = numberFormat(it.budget)
            revenueTv.text = numberFormat(it.revenue)

            if (it.spoken_languages.isNotEmpty()) {
                for (i in 0 until it.spoken_languages.size) {
                    if (it.production_countries.size <= 1) {
                        spokenLanguagesTv.append(it.spoken_languages[i].english_name)
                    } else {
                        spokenLanguagesTv.append(it.spoken_languages[i].english_name + "  |  ")
                    }
                }
            } else {
                spokenLanguagesTv.text = "No Data Available"
            }

            if (it.genres.isNotEmpty()) {
                for (i in 0 until it.genres.size) {
                    if (it.genres.size <= 1) {
                        movieType.append(it.genres[i].name)
                    } else {
                        movieType.append(it.genres[i].name + "  |  ")
                    }
                }
            }
            else
            {
                movieType.text = "No Data Available"
            }

            if(it.production_countries.isNotEmpty()) {
                for (i in 0 until it.production_countries.size) {
                    if (it.production_countries.size <= 1) {
                        countriesTv.append(it.production_countries[i].name)
                    } else {
                        countriesTv.append(it.production_countries[i].name + "  |  ")
                    }
                }
            }
            else
            {
                countriesTv.text = "No Data Available"
            }

            if(it.production_companies.isNotEmpty()) {
                companiesRecyclerView.visibility = View.VISIBLE
                noCompaniesTv.visibility = View.GONE

                companiesRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                companiesAdapter = CompaniesAdapter(this, it.production_companies)
                companiesRecyclerView.adapter = companiesAdapter
            }
            else
            {
                noCompaniesTv.visibility = View.VISIBLE
                companiesRecyclerView.visibility = View.GONE
            }

            commonUtils.dismissCustomDialog(dialog)

        })
    }

    private fun init()
    {
        commonUtils = CommonUtils(this)
        dialog = commonUtils.createCustomLoader(this, false)
        commonUtils.showCustomDialog(dialog,this)
    }

    fun numberFormat (number: Number): String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val numValue = number.toLong()
        val value = floor(log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / 10.0.pow((base * 3).toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    private fun bottomNavigation() {

        bottomNavigationView.setActiveNavigationIndex(0)

        bottomNavigationView.setOnNavigationItemChangedListener(object :
            OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    1 -> {
                        val intent = Intent(this@DetailsActivity, CastActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@DetailsActivity, VideoActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@DetailsActivity, ImageActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }
}