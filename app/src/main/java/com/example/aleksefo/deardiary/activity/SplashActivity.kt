package com.example.aleksefo.deardiary.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.example.aleksefo.deardiary.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val actionBar = supportActionBar
        actionBar?.hide()
        val tapImage = findViewById(R.id.tap_image) as ImageView
        tapImage.setOnClickListener {
            val tapIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(tapIntent)
        }
    }

    companion object {

        private val TAG = SplashActivity::class.java.simpleName
    }
}