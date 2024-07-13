package com.example.app_store_application.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val gameDao = AppDatabase.getInstance(application).gameDao()
    private val _games =
        MutableLiveData<List<GameEntity>>() // MutableLiveData to hold list of games
    val games: LiveData<List<GameEntity>> get() = _games // Expose LiveData

    init {
        // Initialize games in ViewModel
        viewModelScope.launch(Dispatchers.IO) {
            _games.postValue(gameDao.getAllGames()) // Post initial list of games to LiveData
        }
    }

    fun optimizeGame(game: GameEntity, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Simulate some delay
                Thread.sleep(3000)
                gameDao.updateGame(game.apply { isOptimized = true })
                onComplete(true) // Pass true if optimization succeeds
            } catch (e: Exception) {
                onComplete(false) // Pass false if optimization fails
            }
        }
    }

    fun deleteGame(gameId: Int, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                gameDao.deleteGameById(gameId)
                _games.postValue(gameDao.getAllGames())
                onComplete(true) // Pass true if deletion succeeds
            } catch (e: Exception) {
                onComplete(false) // Pass false if deletion fails
            }
        }
    }
}
