package com.example.rtsp_testkotlin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class MainActivity : AppCompatActivity() {
    val TAG : String = "VLCVideoPlayerExample"

    private var url : String = "rtsp://192.168.0.9:8080/h264_pcm.sdp"

    private lateinit var mLibVLC : LibVLC
    private lateinit var mMediaPlayer : MediaPlayer
    private lateinit var mVideoLayout : VLCVideoLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLibVLC = LibVLC(this)
        mMediaPlayer = MediaPlayer(mLibVLC)
        mVideoLayout = findViewById(R.id.video_layout)
    }

    override fun onStart() {
        super.onStart()
        mMediaPlayer.attachViews(mVideoLayout, null, false, false)

        val media = Media(mLibVLC, Uri.parse(url))
        //media.setHWDecoderEnabled(true, false)
        //media.addOption(":network-caching=600")

        mMediaPlayer.media = media
        media.release()
        mMediaPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        mMediaPlayer.stop()
        mMediaPlayer.detachViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.release()
        mLibVLC.release()
    }
}