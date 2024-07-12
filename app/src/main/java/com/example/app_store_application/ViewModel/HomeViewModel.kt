package com.example.app_store_application.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val database: AppDatabase) : ViewModel() {

    val games = liveData(Dispatchers.IO) {
        val gamesList = database.gameDao().getAllGames()
        emit(gamesList)
    }
    fun deleteGame(game: GameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.gameDao().deleteGame(game)
        }
    }

    // Function to optimize a game (placeholder for future functionality)
    fun optimizeGame(game: GameEntity) {
        // Placeholder for future implementation
    }

    // Function to edit a game (placeholder for future functionality)
    fun editGame(game: GameEntity) {
        // Placeholder for future implementation
    }
}
