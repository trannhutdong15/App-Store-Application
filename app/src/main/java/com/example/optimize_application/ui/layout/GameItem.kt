package com.example.optimize_application.ui.layout

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
import com.example.optimize_application.R
import com.example.optimize_application.database.GameEntity


@Composable
fun GameItem(game: GameEntity, onDeleteClick: (GameEntity) -> Unit, onOptimizeClick: (GameEntity) -> Unit) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Image(
            painter = rememberAsyncImagePainter(game.imageGame),
            contentDescription = "Game Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Top),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = game.gameName,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onOptimizeClick(game)
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (game.isOptimized) Color.Green else Color.Blue
                )
            ) {
                Text(text = if (game.isOptimized) "Optimized!" else "Optimize", color = Color.Gray)
            }
        }

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

