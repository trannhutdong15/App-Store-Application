package com.example.app_store_application.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGameViewModel(application: Application) : AndroidViewModel(application) {
    private val gameDao = AppDatabase.getInstance(application).gameDao()

    fun insertGame(game: GameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            gameDao.insertGame(game)
        }
    }
}

class AddGameViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddGameViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
