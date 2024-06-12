package com.example.feesight_mobile.view.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.feesight_mobile.databinding.ActivityLoginBinding
import com.example.feesight_mobile.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var prosesdialog: ProgressDialog
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView4.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        prosesdialog = ProgressDialog(this)
        prosesdialog.setTitle("LOGGING")
        prosesdialog.setMessage("PLEASE WAIT ...")
        binding.btnlogins.setOnClickListener{
            val email = binding.edemail.text.toString()
            val password = binding.edpassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                proseslogin()
            }else{
                Toast.makeText(this,"PLEASE FILL EMAIL AND PASSWORD !!!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun proseslogin() {
        val email = binding.edemail.text.toString()
        val password = binding.edpassword.text.toString()

        prosesdialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                prosesdialog.dismiss()
            }
    }
}
