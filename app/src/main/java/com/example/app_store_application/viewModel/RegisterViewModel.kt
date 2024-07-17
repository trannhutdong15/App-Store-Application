package com.example.app_store_application.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.UserEntity
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getInstance(application).userDao()

    fun registerUser(fullName: String, email: String, phoneNumber: String, password: String) {
        val user = UserEntity(
            name = fullName,
            email = email,
            phoneNumber = phoneNumber,
            password = password
        )
        viewModelScope.launch {
            userDao.insertUser(user)
        }
    }
}

class RegisterViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
