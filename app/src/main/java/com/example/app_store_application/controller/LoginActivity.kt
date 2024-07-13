package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.app_store_application.R
import com.example.app_store_application.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var btnSignin: Button
    private lateinit var btnCreateAccount: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignin = findViewById(R.id.btnSignIn)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        editTextEmail = findViewById(R.id.etEmailLogin)
        editTextPassword = findViewById(R.id.etPasswordLogin)

        btnSignin.setOnClickListener {
            handleSignIn()
        }
        btnCreateAccount.setOnClickListener {
            handleCreateAccount()
        }
    }

    private fun handleSignIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            return
        }

        // Perform email and password validation
        lifecycleScope.launch {
            try {
                val userDao = AppDatabase.getInstance(this@LoginActivity).userDao()
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmailAndPassword(email, password)
                }

                if (user != null) {
                    // Navigate to com.example.app_store_application.controller.com.example.app_store_application.controller.HomeActivity if credentials are correct
                    Toast.makeText(this@LoginActivity, "Sign in successfully!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Show error message if credentials are incorrect
                    Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error during sign-in", e)
                Toast.makeText(this@LoginActivity, "An error occurred during sign-in", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleCreateAccount() {
        // Navigate to RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
