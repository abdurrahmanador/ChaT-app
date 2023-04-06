package com.example.chat.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        emailEditText = binding.emailET
        passwordEditText = binding.passET
        progressDialog= ProgressDialog(this)

        binding.signupAgain.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, ChatFeed::class.java))
        } else {

            binding.loginButton.setOnClickListener {

                val email = emailEditText.text?.toString()
                val password = passwordEditText.text?.toString()

                if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                    Toast.makeText(
                        this, "Please Provide your E-mail and Password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    progressDialog.setMessage("Logging...")
                    progressDialog.show()
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            if (it.isSuccessful) {
                                val user = auth.currentUser
                                startActivity(Intent(this, ChatFeed::class.java))
                            } else {
                                // Log.w(TAG, "signInWithEmail:failure", it.exception)
                                Toast.makeText(
                                    this, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

        }
    }
}
