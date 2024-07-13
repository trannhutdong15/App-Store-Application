package com.example.app_store_application.controller

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_store_application.R
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.database.Converters
import com.example.app_store_application.viewModel.AddGameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGameActivity : AppCompatActivity() {

    private val viewModel: AddGameViewModel by viewModels()

    private lateinit var btnSave: Button
    private lateinit var ivSelectedImage: ImageView
    private lateinit var etGameName: EditText
    private lateinit var etGameUrl: EditText
    private lateinit var btnSelectImage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        btnSave = findViewById(R.id.btnbuttonSaveGame)
        ivSelectedImage = findViewById(R.id.ivimageViewGame)
        etGameName = findViewById(R.id.etTextGameName)
        etGameUrl = findViewById(R.id.etTextGameUrl)
        btnSelectImage = findViewById(R.id.btnbuttonSelectImage)

        btnSelectImage.setOnClickListener {
            selectImage()
        }
        val backButton: ImageButton = findViewById(R.id.backButton2)
        backButton.setOnClickListener {
            navigateBack()
        }

        btnSave.setOnClickListener {
            val gameName = etGameName.text.toString().trim()
            val gameUrl = etGameUrl.text.toString().trim()

            viewModel.setGameName(gameName)
            viewModel.setGameUrl(gameUrl)

            if (viewModel.gameName.value.isNullOrEmpty() || viewModel.gameUrl.value.isNullOrEmpty() || viewModel.selectedImageBitmap.value == null) {
                Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
            } else {
                saveGame(viewModel.gameName.value!!, viewModel.gameUrl.value!!, viewModel.selectedImageBitmap.value!!)
            }
        }

        viewModel.selectedImageBitmap.observe(this) { bitmap ->
            bitmap?.let {
                ivSelectedImage.setImageBitmap(it)
            }
        }
    }
    private fun navigateBack() {
        // Navigate back to HomeActivity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Finish AddGameActivity to prevent it from staying in the back stack
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
                viewModel.setSelectedImageBitmap(bitmap)
            }
        }
    }

    private fun saveGame(gameName: String, gameUrl: String, gameImage: Bitmap) {
        val gameImageByteArray = Converters().fromBitmap(gameImage)

        val gameEntity = GameEntity(
            gameName = gameName,
            imageGame = gameImageByteArray,
            gameUrl = gameUrl
        )

        CoroutineScope(Dispatchers.IO).launch {
            val gameDao = AppDatabase.getInstance(applicationContext).gameDao()
            try {
                gameDao.insertGame(gameEntity)
                runOnUiThread {
                    Toast.makeText(this@AddGameActivity, "Game saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@AddGameActivity, "Error saving game: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }
    }
}
