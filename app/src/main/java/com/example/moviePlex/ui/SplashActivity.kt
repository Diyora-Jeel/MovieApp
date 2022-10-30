package com.example.moviePlex.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.moviePlex.R
import com.example.moviePlex.constants.AppConstants

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences(AppConstants.MOVIE_FILE, MODE_PRIVATE)
        val show = sharedPreferences.getBoolean(AppConstants.ONBOARD,false)

        Handler(mainLooper).postDelayed(Runnable {
            if(show) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
            else
            {
                val i = Intent(this, OnboardActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 1500)
    }
}