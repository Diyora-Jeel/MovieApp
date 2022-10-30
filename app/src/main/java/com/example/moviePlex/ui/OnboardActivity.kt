package com.example.moviePlex.ui

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aemerse.onboard.OnboardAdvanced
import com.aemerse.onboard.OnboardFragment
import com.example.moviePlex.R
import com.example.moviePlex.constants.AppConstants

class OnboardActivity : OnboardAdvanced() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var myEdit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE,MODE_PRIVATE)
        myEdit = sharedPreferences.edit()

        addSlide(
            OnboardFragment.newInstance(
                title = "Search Latest Movie",
                description = "Easily Find Latest Movies, get Information regarding Movie and Free Download Movie Scene in our Platform",
                resourceId = R.raw.research,
                backgroundDrawable = R.color.bg,
                titleColor = Color.WHITE,
                descriptionColor = resources.getColor(R.color.text),
                backgroundColor = resources.getColor(R.color.bg),
                isLottie = true
            )
        )
        addSlide(
            OnboardFragment.newInstance(
                title = "Information of Latest Movie",
                description = "Movie Information Like,Superstar, Rating,Producer Company, Director and Important Information.",
                resourceId = R.raw.watching_movie, //or R.raw.your_json for LottieAnimationView
                backgroundDrawable = R.color.bg,
                titleColor = Color.WHITE,
                descriptionColor = resources.getColor(R.color.text),
                backgroundColor = resources.getColor(R.color.bg),
                isLottie = true
            )
        )

        addSlide(
            OnboardFragment.newInstance(
                title = "Free Download Movie Scene Images",
                description = "High Quality Movie Scene Images Download in our platform",
                resourceId = R.raw.downloading, //or R.raw.your_json for LottieAnimationView
                backgroundDrawable = R.color.bg,
                titleColor = Color.WHITE,
                descriptionColor = resources.getColor(R.color.text),
                backgroundColor = resources.getColor(R.color.bg),
                isLottie = true
            )
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        myEdit.putBoolean(AppConstants.ONBOARD, true)
        myEdit.commit()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        myEdit.putBoolean(AppConstants.ONBOARD, true)
        myEdit.commit()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}