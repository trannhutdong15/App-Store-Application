package com.example.app_store_application.layout


import android.content.Context
import android.net.Uri
import com.example.app_store_application.viewModel.AddGameViewModel
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.app_store_application.R
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.ui.theme.Purple



@Composable
fun AddGameScreen(navController: NavController,
                addgameViewModel : AddGameViewModel
) {
    var gameName by remember { mutableStateOf("") }
    var gameUrl by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }


    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back Arrow",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Add Game",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.width(48.dp))
            }
            OutlinedTextField(
                value = gameName,
                onValueChange = {gameName = it},
                label = { Text("Game Name", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                        shape = RoundedCornerShape(22.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Purple,
                    focusedBorderColor = Purple,

                    )
            )
            OutlinedTextField(
                value = gameUrl,
                onValueChange = {gameUrl=it},
                label = { Text("Game URL or Path", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                        shape = RoundedCornerShape(22.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Purple,
                    focusedBorderColor = Purple,

                )
            )
            Button(
                onClick = {launcher.launch("image/*")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                        )
            ) {
                Text(text = "Select Image", color = Color.White)
            }
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                )
            }


            Button(
                onClick = {
                    if (gameName.isNotBlank() && gameUrl.isNotBlank() && selectedImageUri != null) {
                        // Read the selected image as a ByteArray
                        val imageByteArray = readImageByteArray(selectedImageUri!!,context)
                        val game = GameEntity(
                            gameName = gameName,
                            imageGame = imageByteArray,
                            gameUrl = gameUrl
                        )
                        addgameViewModel.insertGame(game)
                        Toast.makeText(context, "Game added successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "Add", color = Color.White)
            }


        }
    }
}
private fun readImageByteArray(uri: Uri, context: Context): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
    return inputStream?.readBytes() ?: byteArrayOf()
}

