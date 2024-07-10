package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_store_application.database.UserDatabase
import com.example.app_store_application.database.UserEntity
import com.example.app_store_application.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            //Use dataBinding to get information through xml file
            val fullName = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val phoneNumber = binding.etPhonenumber.text.toString()

            if (password == confirmPassword) {
                val user = UserEntity(
                    id = 0,
                    email = email,
                    password = password,
                    name = fullName,
                    phoneNumber = phoneNumber
                )

                insertUser(user)
                showToast("Sign Up Successful!")
                navigateToLogin()
            } else {
                showToast("Passwords do not match!")
            }
        }
    }
    //call userDao to insert user in when they have finished
    private fun insertUser(user: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            UserDatabase.getDatabase(applicationContext).userDao().insert(user)
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    //Navigate back to login if they succefully create their account
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
