package com.example.app_store_application.controller
import android.content.Intent
import android.os.Bundle
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

        // Perform email and password validation
        lifecycleScope.launch {
            val userDao = AppDatabase.getInstance(this@LoginActivity).userDao()
            val user = withContext(Dispatchers.IO) {
                userDao.getUserByEmailAndPassword(email, password)
            }

            if (user != null) {
                // Navigate to HomePageActivity if credentials are correct
                Toast.makeText(this@LoginActivity, "Sign in successfully!!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Show error message if credentials are incorrect
                Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
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
