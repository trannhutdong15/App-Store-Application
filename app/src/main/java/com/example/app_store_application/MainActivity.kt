package com.example.app_store_application

import LoginScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.ui.theme.AppTheme
import com.example.app_store_application.viewModel.LoginViewModel
import com.example.app_store_application.viewModel.LoginViewModelFactory
import com.example.app_store_application.viewModel.RegisterViewModel
import com.example.app_store_application.viewModel.RegisterViewModelFactory
import com.example.app_store_application.layout.HomeScreen
import com.example.app_store_application.layout.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MyApp()
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
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") { LoginScreen(navController, loginViewModel) }
        composable("register_screen") { RegisterScreen(navController, registerViewModel) }
        composable("home_screen") { HomeScreen() }
    }
}
