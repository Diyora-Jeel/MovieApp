package com.example.moviePlex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviePlex.R
import com.example.moviePlex.constants.AppConstants
import com.example.moviePlex.constants.CommonUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.activity_video_details.*

class VideoDetailsActivity : AppCompatActivity() {

    lateinit var videoId : String
    private lateinit var commonUtils: CommonUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)

        commonUtils = CommonUtils(this)

        videoId = intent.getStringExtra(AppConstants.VIDEO).toString()

        youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.play()
            }
        })
    }
}