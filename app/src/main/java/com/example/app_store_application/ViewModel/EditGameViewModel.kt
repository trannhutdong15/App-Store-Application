package com.example.app_store_application.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditGameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameDao = AppDatabase.getInstance(application).gameDao()

    fun getGameById(gameId: Int): LiveData<GameEntity> {
        return gameDao.getGameById(gameId)
    }

    fun updateGame(game: GameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            gameDao.updateGame(game)
        }
    }
}
