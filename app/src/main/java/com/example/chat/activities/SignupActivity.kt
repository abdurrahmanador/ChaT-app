package com.example.chat.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var progressDialog: ProgressDialog


    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        name = binding.namET
        email = binding.emailET
        password = binding.passET
        confirmPassword = binding.confirmpassET
        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        binding.loginAgain.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.signupButton.setOnClickListener {
            val emailStr = email.text?.toString()
            val passStr = password.text?.toString()
            val confirmPasswordStr = confirmPassword.text?.toString()
            if (!emailStr.isNullOrEmpty() && !passStr.isNullOrEmpty() && passStr == confirmPasswordStr) {
                progressDialog.setMessage("Signing Up...")
                progressDialog.show()
                auth.createUserWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this) { task ->
                        progressDialog.dismiss()
                        if (task.isSuccessful) {
                            startActivity(Intent(this, ChatFeed::class.java))
                            val user = hashMapOf(
                                "name" to name.text.toString(),
                                "email" to emailStr
                            )
                            db.collection("users")
                                .document(auth.currentUser!!.uid)
                                .set(user)
                                .addOnSuccessListener {

                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this@SignupActivity,
                                        "Provide all Data Correctly",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            startActivity(Intent(this, ChatFeed::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                "Failed to signup",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else if (passStr != confirmPasswordStr) {
                Toast.makeText(this@SignupActivity, "Check the password Again", Toast.LENGTH_SHORT)
                    .show()
            } else if (emailStr.isNullOrEmpty()) {
                Toast.makeText(this@SignupActivity, "Please Provide an email.", Toast.LENGTH_SHORT)
                    .show()
            } else if (passStr.isNullOrEmpty() || confirmPasswordStr.isNullOrEmpty()) {
                Toast.makeText(
                    this@SignupActivity,
                    "Choose a Password or Confirm your password.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }


}