package com.example.feesight_mobile.view.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.feesight_mobile.databinding.ActivityRegisterBinding
import com.example.feesight_mobile.view.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
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
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textView6.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        prosesdialog = ProgressDialog(this)
        prosesdialog.setTitle("Signing UP")
        prosesdialog.setMessage("PLEASE WAIT ...")

        binding.btnsignup.setOnClickListener{
            val nama = binding.ednama.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val confirm = binding.editTextTextPassword2.text.toString()
            if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if (password == confirm) {
                    prosesregister()
                }else{
                    Toast.makeText(this,"PASSWORD MUST MATCH !!!",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"PLEASE FILL IN ALL THE FIELDS !!!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prosesregister(){
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()
        val nama = binding.ednama.text.toString()

        prosesdialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    val userUpdate = userProfileChangeRequest {
                        displayName = nama
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdate)
                        .addOnCompleteListener {
                            prosesdialog.dismiss()
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                }else{
                    prosesdialog.dismiss()
                }
            }
            .addOnFailureListener { error2 ->
                Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}