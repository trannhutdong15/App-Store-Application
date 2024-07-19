package com.example.optimize_application.viewModel

import android.widget.Toast
import androidx.lifecycle.*
import com.example.optimize_application.database.GameDao
import com.example.optimize_application.database.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(private val gameDao: GameDao) : ViewModel() {
    val games: LiveData<List<GameEntity>> = gameDao.getAllGames()


    // Handle delete games from gameDao query
    fun deleteGame(game: GameEntity) {
        viewModelScope.launch {
            gameDao.deleteGame(game)
        }
    }

    // Handle optimize games from gameDao query
    fun optimizeGame(game: GameEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            delay(3000)
            game.isOptimized = true
            gameDao.updateGame(game)
        }
    }
}

// Initialize custom viewModel for GameScreen
class GameViewModelFactory(private val gameDao: GameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(gameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
