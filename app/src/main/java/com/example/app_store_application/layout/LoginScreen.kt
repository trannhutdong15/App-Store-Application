
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.app_store_application.R  // Adjust package name as per your actual project structure
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_store_application.ui.theme.ButtonBackground
import com.example.app_store_application.viewModel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController,
                loginViewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    // State for email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = Color.Black.copy(alpha = 0.1f), // Slight black background
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log In",
                fontSize = 24.sp,
                color = Color.White, // White text color
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = { /* Handle Google Sign In */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBackground
            )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Text(   text = "Continue with Google",
                        color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Button(
                onClick = { /* Handle Facebook Sign In */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBackground
                        )

            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(   text = "Continue with Google",
                        color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Google Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            OutlinedTextField(
                value = email,
                onValueChange = {  email = it },
                label = { Text("Email", color = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) ,
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        visualTransformation = PasswordVisualTransformation()

            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Don't have an account?",
                    color = Color.Gray
                )
                TextButton(
                    onClick = { navController.navigate("register_screen") },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(text = "Create a new account", color = Color.White)
                }
            }
            Button(
                onClick = {
                    loginViewModel.loginUser(email, password) { isSuccess ->
                        if (isSuccess) {
                            Toast.makeText(context, "Sign In Successfully!!!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home_screen")
                        } else {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBackground
                )
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White
                )
            }
        }
    }
}

