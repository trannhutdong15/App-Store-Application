package com.example.app_store_application.controller

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_store_application.R
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.database.Converters
import com.example.app_store_application.ViewModel.EditGameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditGameActivity : AppCompatActivity() {

    private val viewModel: EditGameViewModel by viewModels()

    private lateinit var btnSave: Button
    private lateinit var ivSelectedImage: ImageView
    private lateinit var etGameName: EditText
    private lateinit var etGameUrl: EditText
    private lateinit var gameEntity: GameEntity // Assume this is passed from com.example.app_store_application.controller.HomeActivity or wherever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_game)

        btnSave = findViewById(R.id.btnbuttonUpdateGame)
        ivSelectedImage = findViewById(R.id.ivimageViewGameedit)
        etGameName = findViewById(R.id.etTextGameNameedit)
        etGameUrl = findViewById(R.id.etTextGameUrledit)

        // Retrieve game data to edit (passed from previous activity)
        gameEntity = intent.getParcelableExtra("game_entity")
            ?: throw IllegalStateException("GameEntity must not be null")

        // Initialize ViewModel with existing game data
        viewModel.setGameData(gameEntity)

        // Observe ViewModel LiveData to update UI
        viewModel.gameName.observe(this) { name ->
            etGameName.setText(name)
        }
        viewModel.gameUrl.observe(this) { url ->
            etGameUrl.setText(url)
        }
        viewModel.selectedImageBitmap.observe(this) { bitmap ->
            bitmap?.let {
                ivSelectedImage.setImageBitmap(it)
            }
        }

        // Select image button click listener
        ivSelectedImage.setOnClickListener {
            selectImage()
        }

        // Save button click listener
        btnSave.setOnClickListener {
            val updatedGameName = etGameName.text.toString().trim()
            val updatedGameUrl = etGameUrl.text.toString().trim()

            viewModel.updateGameName(updatedGameName)
            viewModel.updateGameUrl(updatedGameUrl)

            if (viewModel.gameName.value.isNullOrEmpty() || viewModel.gameUrl.value.isNullOrEmpty() || viewModel.selectedImageBitmap.value == null) {
                Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
            } else {
                saveUpdatedGame()
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            selectedImageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                viewModel.updateSelectedImageBitmap(bitmap)
            }
        }
    }

    private fun saveUpdatedGame() {
        // Update gameEntity with new data
        gameEntity.apply {
            // Update game name if changed
            viewModel.gameName.value?.let {
                if (it.isNotBlank()) {
                    gameName = it
                }
            }

            // Update game URL if changed
            viewModel.gameUrl.value?.let {
                if (it.isNotBlank()) {
                    gameUrl = it
                }
            }

            // Update game image if changed
            viewModel.selectedImageBitmap.value?.let { newBitmap ->
                val newImageByteArray = Converters().fromBitmap(newBitmap)
                if (!newImageByteArray.contentEquals(imageGame)) {
                    imageGame = newImageByteArray
                }
            }
        }

        // Update gameEntity in database
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gameDao = AppDatabase.getInstance(applicationContext).gameDao()
                gameDao.updateGame(gameEntity)
                runOnUiThread {
                    Toast.makeText(this@EditGameActivity, "Game updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@EditGameActivity, "Error updating game: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }
    }

}


