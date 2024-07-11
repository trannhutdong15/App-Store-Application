package com.example.app_store_application.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//Manages data and logic related to adding games,save user input , storing into database
//AddGaeViewModel purpose
class AddGameViewModel : ViewModel() {
    //Get game name after input successfully
    private val _gameName = MutableLiveData<String>()
    val gameName: LiveData<String> get() = _gameName

    private val _gameUrl = MutableLiveData<String>()
    //Get game url after input successfully
    val gameUrl: LiveData<String> get() = _gameUrl

    private val _selectedImageBitmap = MutableLiveData<Bitmap?>()

    //Get game image after input successfully
    val selectedImageBitmap: LiveData<Bitmap?> get() = _selectedImageBitmap
    //Take all the image to HomeActivity to use
    fun setGameName(name: String) {
        _gameName.value = name
    }

    fun setGameUrl(url: String) {
        _gameUrl.value = url
    }

    fun setSelectedImageBitmap(bitmap: Bitmap?) {
        _selectedImageBitmap.value = bitmap
    }
}
