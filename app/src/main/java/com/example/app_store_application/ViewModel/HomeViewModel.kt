package com.example.app_store_application.ViewModel

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.controller.EditGameActivity
import kotlinx.coroutines.launch

import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class HomeViewModel(private val database: AppDatabase) : ViewModel() {

    val games = liveData(Dispatchers.IO) {
        val gamesList = database.gameDao().getAllGames()
        emit(gamesList)
    }
    fun deleteGame(game: GameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.gameDao().deleteGameById(game.id)
        }
    }

    // Function to optimize a game (placeholder for future functionality)
    fun optimizeGame(game: GameEntity, viewModelScope: CoroutineScope) {
        viewModelScope.launch {
            delay(5000) // Simulate a 5-second delay
            println("Optimize Successfully")
        }
    }
}
