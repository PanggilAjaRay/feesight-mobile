package com.example.feesight_mobile.view.landingpage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feesight_mobile.databinding.ActivityLandingpageBinding
import com.example.feesight_mobile.view.home.HomeActivity
import com.example.feesight_mobile.view.login.LoginActivity
import com.example.feesight_mobile.view.login.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LandingpageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingpageBinding
    lateinit var prosesdialog: ProgressDialog
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.btnregis.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}