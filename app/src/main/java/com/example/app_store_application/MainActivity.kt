package com.example.app_store_application

import LoginScreen
import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.layout.AddGameScreen
import com.example.app_store_application.ui.theme.AppTheme
import com.example.app_store_application.viewModel.AddGameViewModel
import com.example.app_store_application.viewModel.AddGameViewModelFactory
import com.example.app_store_application.viewModel.LoginViewModel
import com.example.app_store_application.viewModel.LoginViewModelFactory
import com.example.app_store_application.viewModel.RegisterViewModel
import com.example.app_store_application.viewModel.RegisterViewModelFactory
import com.example.app_store_application.layout.HomeScreen
import com.example.app_store_application.layout.RegisterScreen
import com.example.app_store_application.viewModel.GameViewModel
import com.example.app_store_application.viewModel.GameViewModelFactory

class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MyApp()
            }
        }
        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permissionStatus: Boolean

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED

            if (!permissionStatus) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    REQUEST_CODE
                )
            }
        } else {
            permissionStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

            if (!permissionStatus) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE
                )
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(AppDatabase.getInstance(context).userDao())
    )
    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(context.applicationContext as Application)
    )
    val addGameViewModel: AddGameViewModel = viewModel(
        factory = AddGameViewModelFactory(context.applicationContext as Application)
    )
    val gameViewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(AppDatabase.getInstance(context).gameDao())
    )
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") { LoginScreen(navController, loginViewModel) }
        composable("register_screen") { RegisterScreen(navController, registerViewModel) }
        composable("home_screen") { HomeScreen(navController,gameViewModel) }
        composable("add_game_screen") { AddGameScreen(navController, addGameViewModel) }
    }
}
