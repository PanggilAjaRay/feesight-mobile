package com.example.feesight_mobile.view.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feesight_mobile.data.request.LoginRequest
import com.example.feesight_mobile.data.response.LoginResponse
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.ActivityLoginBinding
import com.example.feesight_mobile.view.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var prosesdialog: ProgressDialog

    override fun onStart() {
        super.onStart()
        ApiConfig.initialize(this) // Inisialisasi ApiConfig dengan konteks
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        Log.d("TOKEN_LOG", token.toString())
        if (token != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView4.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        prosesdialog = ProgressDialog(this)
        prosesdialog.setTitle("LOGGING")
        prosesdialog.setMessage("PLEASE WAIT ...")

        binding.btnlogins.setOnClickListener {
            val email = binding.edemail.text.toString()
            val password = binding.edpassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                proseslogin(email, password)
            } else {
                Toast.makeText(this, "PLEASE FILL EMAIL AND PASSWORD !!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun proseslogin(email: String, password: String) {
        prosesdialog.show()

        val loginRequest = LoginRequest(email, password)

        lifecycleScope.launch {
            try {
                val loginResponse: Response<LoginResponse> = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().login(loginRequest).execute()
                }

                if (loginResponse.isSuccessful) {
                    val token = loginResponse.body()?.token
                    val name = loginResponse.body()?.displayName
                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("token", token).apply()
                    sharedPreferences.edit().putString("name", name)
                    prosesdialog.dismiss()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    prosesdialog.dismiss()
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                prosesdialog.dismiss()
                Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
