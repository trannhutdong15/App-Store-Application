package com.example.app_store_application.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val database: AppDatabase) : ViewModel() {

    val games = liveData(Dispatchers.IO) {
        val gamesList = database.gameDao().getAllGames()
        emit(gamesList)
    }
}
