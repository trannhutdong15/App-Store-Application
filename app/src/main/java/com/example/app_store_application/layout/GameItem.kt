package com.example.app_store_application.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.app_store_application.R // Adjust this as per your actual project structure
import com.example.app_store_application.database.GameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameItem(game: GameEntity, onDeleteClick: (GameEntity) -> Unit) {
    var optimized by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))  // Clip the entire Row with rounded corners
            .background(Color.LightGray.copy(alpha = 0.3f))  // Light gray with 30% transparency
    ) {
        // Display game image
        Image(
            painter = rememberAsyncImagePainter(game.imageGame),
            contentDescription = "Game Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Top),
            contentScale = ContentScale.Crop
        )

        // Game details and optimize button
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            // Display game name
            Text(
                text = game.gameName,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Spacer
            Spacer(modifier = Modifier.height(24.dp))

            // Optimize button(check if optimize click or not to change color
            if (optimized) {
                Text(
                    text = "Optimized",
                    color = Color.Green,
                )
            } else {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.Default).launch {
                            delay(3000L)
                            optimized = true
                        }
                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    )
                ) {
                    Text(text = if (optimized) "Optimized" else "Optimize", color = Color.White)
                }
            }
        }

        // Spacer to push the delete button to the end

        // Delete button
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp)
                .clickable { onDeleteClick(game) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
