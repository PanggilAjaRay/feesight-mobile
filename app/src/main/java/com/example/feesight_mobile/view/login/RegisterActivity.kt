package com.example.feesight_mobile.view.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feesight_mobile.data.request.LoginRequest
import com.example.feesight_mobile.data.request.RegisterRequest
import com.example.feesight_mobile.data.response.LoginResponse
import com.example.feesight_mobile.data.response.RegisterResponse
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.ActivityRegisterBinding
import com.example.feesight_mobile.view.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var prosesdialog: ProgressDialog

    override fun onStart() {
        super.onStart()
        ApiConfig.initialize(this) // Inisialisasi ApiConfig dengan konteks
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView6.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        prosesdialog = ProgressDialog(this)
        prosesdialog.setTitle("Signing UP")
        prosesdialog.setMessage("PLEASE WAIT ...")

        binding.btnsignup.setOnClickListener {
            val nama = binding.ednama.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val confirm = binding.editTextTextPassword2.text.toString()
            if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if (password == confirm) {
                    prosesregister(nama, email, password)
                } else {
                    Toast.makeText(this, "PASSWORD MUST MATCH !!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "PLEASE FILL IN ALL THE FIELDS !!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prosesregister(nama: String, email: String, password: String) {
        prosesdialog.show()

        val registerRequest = RegisterRequest(email, password, nama)

        lifecycleScope.launch {
            try {
                val registerResponse: Response<RegisterResponse> = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().register(registerRequest).execute()
                }

                if (registerResponse.isSuccessful) {
                    proseslogin(email, password)
                } else {
                    prosesdialog.dismiss()
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                prosesdialog.dismiss()
                Toast.makeText(this@RegisterActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun proseslogin(email: String, password: String) {
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
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                } else {
                    prosesdialog.dismiss()
                    Toast.makeText(this@RegisterActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                prosesdialog.dismiss()
                Toast.makeText(this@RegisterActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
