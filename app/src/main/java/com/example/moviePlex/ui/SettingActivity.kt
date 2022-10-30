package com.example.moviePlex.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviePlex.BuildConfig
import com.example.moviePlex.R
import com.example.moviePlex.constants.CommonUtils
import com.example.moviePlex.constants.Presets
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.bottomNavigationView

class SettingActivity : AppCompatActivity() {

    private val privacyPolicyUrl = "https://appdevlopmentprivacypolicy.blogspot.com/2022/04/movieplex-privacy-policy.html"
    private val playStoreUrl = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"

    var click = false

    private lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        bottomNavigation()
        init()

        rate.setOnClickListener {
            click = true
            val uri: Uri = Uri.parse(playStoreUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        share.setOnClickListener {
            try {
                click = true
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                shareIntent.putExtra(Intent.EXTRA_TEXT, playStoreUrl)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
            }
        }

        privacyPolicy.setOnClickListener {
            val uri: Uri = Uri.parse(privacyPolicyUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        if(click) {
            celebrationView.start(Presets.festive())
        }
        click = false
    }

    private fun init() {
        commonUtils = CommonUtils(this)
    }

    private fun bottomNavigation() {

        bottomNavigationView.setActiveNavigationIndex(3)
        bottomNavigationView.setOnNavigationItemChangedListener(object : OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {

                when (navigationItem.position) {
                    0 -> {
                        val intent = Intent(this@SettingActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this@SettingActivity,SearchActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                    2 -> {
                        val intent = Intent(this@SettingActivity,NewsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    }
                }
            }
        })
    }
}