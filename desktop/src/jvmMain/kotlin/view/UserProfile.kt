package view


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.client.addUserDetails
import database.client.fetchUserDetails
import database.client.getUserId
import database.model.UserDetails
import kotlinx.coroutines.runBlocking

import java.util.prefs.Preferences


@Composable
fun UserProfileScreen(onNavigate: () -> Unit) {

    var userData by remember { mutableStateOf<UserDetails?>(null) }
    val userDetails = userData

    var inputName by remember { mutableStateOf("") }
    var inputEmail = getUserEmail()
    var inputContact by remember { mutableStateOf("") }
    var inputRole by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isTextFieldClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val fetchedUserData = fetchUserDetails(userId = getUserId())
        userData = fetchedUserData
        inputName = fetchedUserData?.name ?: ""
        inputContact = fetchedUserData?.contactNumber ?: ""
        inputRole = fetchedUserData?.role ?: ""
    }


    Card(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp), elevation = 20.dp) {
        Column(

            modifier = Modifier.fillMaxWidth().padding(20.dp),
        ) {
            Row {
                Text(
                    text = "User Profile",
                    modifier = Modifier.padding(top = 20.dp, bottom = 36.dp),
                    color = Color(0xFF11009E),
                    fontSize = 30.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                )
                Button(
                    onClick = onNavigate,
                    modifier = Modifier.padding(top = 20.dp, start = 800.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF11009E)
                    )
                ) {
                    Text(text = "Back to Home", color = Color.White)
                }
            }



            OutlinedTextField(
                value = inputName,
                onValueChange = { inputName = it },
                label = {
                    Text(
                        "Name",
                        color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                            0xFF11009E
                        )
                    )
                },
                modifier = Modifier.padding(bottom = 8.dp, end = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                        0xFF11009E
                    )
                )
//                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),

            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = inputEmail,
                onValueChange = { inputEmail = it },
                label = {
                    Text(
                        "Email",
                        color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                            0xFF11009E
                        )
                    )
                },
                modifier = Modifier.padding(bottom = 8.dp, end = 10.dp),
                // isError = inputHours.isNotEmpty() && runCatching { inputHours.toFloat() }.isFailure,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                        0xFF11009E
                    )
                )

            )

            OutlinedTextField(
                value = inputContact,
                onValueChange = { inputContact = it },
                label = {
                    Text(
                        "Contact Number",
                        color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                            0xFF11009E
                        )
                    )
                },
                modifier = Modifier.padding(bottom = 8.dp, end = 10.dp),
                // isError = inputHours.isNotEmpty() && runCatching { inputHours.toFloat() }.isFailure,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                        0xFF11009E
                    )
                )

            )
            OutlinedTextField(
                value = inputRole,
                onValueChange = { inputRole = it },
                label = {
                    Text(
                        "Role",
                        color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                            0xFF11009E
                        )
                    )
                },
                readOnly = true,
                modifier = Modifier.padding(bottom = 8.dp, end = 10.dp),
                // isError = inputHours.isNotEmpty() && runCatching { inputHours.toFloat() }.isFailure,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                        0xFF11009E
                    )
                )

            )
            if (showErrorMessage) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    color = Color.Red
                )
            }
            Button(
                onClick = {
                    if (inputName.isNotEmpty() && inputContact.isNotEmpty()) {
                        val name = inputName
                        val email = inputEmail
                        val contactNumber = inputContact
                        val result =
                            runBlocking {
                                addUserDetails(
                                    name,
                                    email,
                                    contactNumber,

                                )
                            }
                        println(result)
                        onNavigate()
                    } else {
                        showErrorMessage = true
                        errorMessage = "Please Fill All Fields"
                        println("error")
                    }
                }, modifier = Modifier.padding(top = 20.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF11009E)
                )
            ) {
                Text(text = "Submit", color = Color.White)
            }

        }
    }
}

@Composable
fun getUserEmail(): String {
    val preferences = Preferences.userRoot().node("com.example.project")
    return preferences.get("userEmail", "")
}
