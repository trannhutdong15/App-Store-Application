package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.app_store_application.R

class LoginActivity : AppCompatActivity() {

    private lateinit var btnSignin: Button
    private lateinit var btnCreateAccount:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignin = findViewById(R.id.btnSignIn)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        // Set click listener for the sign-in button
        btnCreateAccount.setOnClickListener {
            handleSignIn()
        }
        btnSignin.setOnClickListener {
            handleSignIn_Home()
        }
    }
    private fun handleSignIn_Home() {
        // Navigate to HomePageActivity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun handleSignIn() {
        // Navigate to RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
