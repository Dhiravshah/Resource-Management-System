package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.client.SignIn
import database.client.SignUp
import kotlinx.coroutines.runBlocking
import java.util.prefs.Preferences

@Composable
fun LogInScreen(
    onButtonSignUpClick: () -> Unit,
    onButtonSubmitClick: () -> Unit
) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isTextFieldClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .height(500.dp)
                .width(500.dp)
                .padding(10.dp),
            backgroundColor = Color.White,
            elevation = 7.dp,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(30.dp),

                ) {
                Text(
                    text = "Login Screen",
                    modifier = Modifier.padding(top = 10.dp, bottom = 16.dp),
                    color = Color(0xFF11009E),
                    fontSize = 30.sp,
                    style = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)

                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(30.dp),

                    ) {
                    OutlinedTextField(
                        value = userEmail,
                        onValueChange = { userEmail = it },
                        label = {
                            Text(
                                "Email",
                                color =
                                if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                    0xFF11009E
                                )
                            )
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                            .clickable { isTextFieldClicked = true },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                                0xFF11009E
                            )
                        )

                    )
                    OutlinedTextField(
                        value = userPassword,
                        onValueChange = { userPassword = it },
                        label = {
                            Text(
                                "Password", color =
                                if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                    0xFF11009E
                                )
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                                0xFF11009E
                            )
                        )
                    )

                    if (showErrorMessage) {
                        Text(
                            text = errorMessage,
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val email = userEmail
                            val password = userPassword
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                val result = runBlocking { SignIn(email, password) }
                                println(result)
                                if (result != "failure") {
                                    storeUserEmail(email)
                                    onButtonSubmitClick()
                                } else {
                                    when (result) {
                                        "auth/user-not-found" -> {
                                            showErrorMessage = true
                                            errorMessage = "User not found"
                                        }
                                        "auth/wrong-password" -> {
                                            showErrorMessage = true
                                            errorMessage = "Wrong password"
                                        }
                                        else -> {
                                            showErrorMessage = true
                                            errorMessage = "Authentication failed"
                                        }
                                    }
                                    println(result)
                                }
                            } else {
                                showErrorMessage = true
                            }
                        },
                        modifier = Modifier.padding(top = 20.dp),
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Submit", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onButtonSignUpClick,
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Sign Up", color = Color.White)
                    }
                }
            }
        }

    }
}

private fun storeUserEmail(email: String) {
    val preferences = Preferences.userRoot()
        .node("com.example.project")
    preferences.put("userEmail", email)
    println("my email: $email")
    preferences.flush()
}




