package com.example.app_store_application.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_store_application.database.GameEntity

class EditGameViewModel : ViewModel() {

    private val _gameName = MutableLiveData<String>()
    val gameName: LiveData<String> get() = _gameName

    private val _gameUrl = MutableLiveData<String>()
    val gameUrl: LiveData<String> get() = _gameUrl

    private val _selectedImageBitmap = MutableLiveData<Bitmap?>()
    val selectedImageBitmap: LiveData<Bitmap?> get() = _selectedImageBitmap

    // Method to set initial game data for editing
    fun setGameData(game: GameEntity) {
        _gameName.value = game.gameName
        _gameUrl.value = game.gameUrl
        // Set bitmap if needed, convert from byte array to bitmap
        // Example: _selectedImageBitmap.value = Converters().toBitmap(game.imageGame)
    }

    // Method to update game name
    fun updateGameName(name: String) {
        _gameName.value = name
    }

    // Method to update game URL
    fun updateGameUrl(url: String) {
        _gameUrl.value = url
    }

    // Method to update selected image bitmap
    fun updateSelectedImageBitmap(bitmap: Bitmap?) {
        _selectedImageBitmap.value = bitmap
    }
}
