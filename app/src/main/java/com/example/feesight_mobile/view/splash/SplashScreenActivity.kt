package com.example.feesight_mobile.view.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.feesight_mobile.R
import com.example.feesight_mobile.view.landingpage.LandingpageActivity
import com.example.feesight_mobile.view.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private val splashTime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            setContentView(R.layout.activity_splash_screen)
            Handler().postDelayed({
                startActivity(Intent(this, LandingpageActivity::class.java))
                finish()
            }, splashTime)
        }
    }
    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}