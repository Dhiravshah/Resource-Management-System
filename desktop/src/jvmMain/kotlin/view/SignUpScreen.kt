package view


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.client.SignUp
import kotlinx.coroutines.runBlocking

@Composable
fun SignupScreen(onNavigate: () -> Unit) {
    var userEmail by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isTextFieldClicked by remember { mutableStateOf(false) }
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    val PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{9,}\$"

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
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
                    text = "Register Yourself",
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
                        value = userName,
                        onValueChange = { userName = it },
                        label = {
                            Text(
                                "Name", color =
                                if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                    0xFF11009E
                                )
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                                0xFF11009E
                            )
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = userEmail,
                        onValueChange = { userEmail = it },
                        label = {
                            Text(
                                "Email", color =
                                if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                    0xFF11009E
                                )
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                                0xFF11009E
                            )
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
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
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)

                    )
                    if (showErrorMessage) {
                        Text(
                            text = errorMessage,
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color.Red
                        )
                    }
                    if (showSuccessMessage) {
                        Text(
                            text = errorMessage,
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color(0xFF11009E)
                        )
                    }

                    Button(
                        onClick = {
                            val name = userName
                            val email = userEmail
                            val password = userPassword
                            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                                if (EMAIL_REGEX.toRegex().matches(email)) {
                                    if (PASSWORD_REGEX.toRegex().matches(password)) {
                                        val result = runBlocking {
                                            SignUp(
                                                email = email,
                                                password = password,
                                                name = name
                                            )
                                        }
                                        showSuccessMessage = true
                                        errorMessage = "Data Added Successfully!!"
                                        println(result)
                                        onNavigate()
                                    }else{
                                        showErrorMessage = true
                                        errorMessage = "Password must contain one upercase, one lowercase, one number & one special character"
                                    }
                                }
                                else{
                                    showErrorMessage = true
                                    errorMessage = "Please Enter Valid Email"
                                }
                            } else {
                                showErrorMessage = true
                                errorMessage = "Please Enter The Data"
                            }

                        }, modifier = Modifier.padding(top = 20.dp),
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "REGISTER", color = Color.White)
                    }
                    Button(
                        onClick = {
                            onNavigate()
                        }, modifier = Modifier.padding(top = 20.dp),
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Back To Login", color = Color.White)
                    }
                }
            }
        }
    }
}