package com.example.earsproject.data.uii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.earsproject.CreateAccountActivity
import com.example.earsproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
private lateinit var binding: ActivityLoginBinding
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // Set click listener for login button
        binding.signin.setOnClickListener {
            val email =  binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            // Check if username and password fields are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Perform login process
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

            binding.btnSignup.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        // User exists and password matches
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Start the next activity (e.g., MainActivity)
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish() // Optional: Close the LoginActivity
                    } else {
                        // User does not exist or password does not match
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    // Error occurred while querying Firestore
                    Toast.makeText(
                        this,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}