package com.example.earsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.earsproject.data.uii.MainActivity2
import com.example.earsproject.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateAccountActivity : AppCompatActivity() {
private lateinit var binding : ActivityCreateAccountBinding
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCreateUser.setOnClickListener{
            val firstName = binding.txtCreateFirstName.text.toString()
            val lastName = binding.txtCreateLastName.text.toString()
            val email = binding.txtCreateEmail.text.toString()
            val password = binding.txtCreatePassword.text.toString()

            // Check if any fields are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    // Perform login process
                    createAccount(firstName, lastName, email, password)
                } else {
                    Toast.makeText(this, "Password must contain at least 6 characters", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun createAccount(firstName: String, lastName: String, email: String, password: String) {
        // Create a new user with the provided email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User creation successful
                    val user = auth.currentUser

                    // Save user details to Firestore
                    user?.let {
                        val userMap = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "email" to email,
                            "password" to password
                        )

                        firestore.collection("users")
                            .document(user.uid)
                            .set(userMap)
                            .addOnCompleteListener { saveTask ->
                                if (saveTask.isSuccessful) {
                                    // User details saved to Firestore
                                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity2::class.java)
                                    startActivity(intent)
                                    finish()
                                    // Proceed with the next steps (e.g., navigate to the home screen)
                                } else {
                                    // Failed to save user details to Firestore
                                    Toast.makeText(this, "Failed to create account. ${task.exception?.message}.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    // User creation failed
                    Toast.makeText(this, "Failed to create account. ${task.exception?.message}.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}