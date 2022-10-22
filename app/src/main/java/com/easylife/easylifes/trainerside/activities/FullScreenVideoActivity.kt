package com.easylife.easylifes.trainerside.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.RelativeLayout
import com.easylife.easylifes.R
import com.easylife.easylifes.utils.Utilities
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class FullScreenVideoActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private var playerView: PlayerView? = null
    private var videolink = ""
    private lateinit var utilities: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_full_screen_video)

        utilities = Utilities(this@FullScreenVideoActivity)
        utilities.setWhiteBars(this@FullScreenVideoActivity)
        val intent = intent
        playerView = findViewById(R.id.video_view)
        val ivBack: RelativeLayout = findViewById(R.id.layout_backArrow)
        utilities = Utilities(this@FullScreenVideoActivity)
        videolink = intent.getStringExtra("videourl").toString()
        if (videolink != "") {
            setVideo(videolink)
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setVideo(videoLink: String) {

        val appNameStringRes = R.string.app_name
        val trackSelectorDef: TrackSelector = DefaultTrackSelector(this)
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef)
        val userAgent = Util.getUserAgent(this, this.getString(appNameStringRes))
        val defaultDataSourceFactory = DefaultDataSourceFactory(this, userAgent)
        val uriOfContentUrl = Uri.parse(videoLink)
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(defaultDataSourceFactory)
            .createMediaSource(uriOfContentUrl)
        //MediaSource mediaSource = new ExtractorMediaSource(uriOfContentUrl, new CacheDataSourceFactory(context, 100 * 1024 * 1024, 500 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
        //MediaSource mediaSource = new ExtractorMediaSource(uriOfContentUrl, new CacheDataSourceFactory(context, 100 * 1024 * 1024, 500 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
        player!!.prepare(mediaSource)
        player!!.playWhenReady = false
        playerView!!.requestFocus()
        playerView!!.player = player
        playerView!!.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        player!!.volume = 1f

    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.stop()
        player!!.release()
    }
}
