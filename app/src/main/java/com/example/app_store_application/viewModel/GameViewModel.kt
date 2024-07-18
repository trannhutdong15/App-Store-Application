package com.example.app_store_application.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.database.GameDao
import kotlinx.coroutines.launch

class GameViewModel(private val gameDao: GameDao) : ViewModel() {
    val games: LiveData<List<GameEntity>> = gameDao.getAllGames()
    //Handle delete games from gameDao query
    fun deleteGame(game: GameEntity) {
        viewModelScope.launch {
            gameDao.deleteGame(game)
        }
    }
}
//Initialize custom viewModel for GameScreen
class GameViewModelFactory(private val gameDao: GameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(gameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
