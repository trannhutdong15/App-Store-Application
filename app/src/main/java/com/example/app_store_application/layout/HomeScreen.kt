package com.example.app_store_application.layout

import GameItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_store_application.R // Adjust package name as per your actual project structure
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.viewModel.GameViewModel

@Composable
fun HomeScreen(navController: NavController,gameViewModel: GameViewModel) {
    val games by gameViewModel.games.observeAsState(emptyList())
    val onOptimizeClick: (GameEntity) -> Unit = { game ->

    }
    Surface(2
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome Dong",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Let's optimize your favorite games!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            // Spacer to give some space before the divider
            Spacer(modifier = Modifier.height(16.dp))
            // Box to hold the divider and the button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.BottomEnd // Align the content to the top end of the Box
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                IconButton(
                    onClick = { navController.navigate("add_game_screen") },
                    modifier = Modifier.size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add), // Adjust the resource id as per your actual project structure
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp) // Adjust the size as needed
                    )
                }
            }
            // Spacer to give some space after the divider
            Spacer(modifier = Modifier.height(24.dp))
            // Display the list of games
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(games) { game ->
                    GameItem(game,onOptimizeClick)
                }
            }
        }
    }
}
