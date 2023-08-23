package uidata


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import component.UserUpdateDialog
import database.client.updateUserDetails
import database.model.UserDetails
import kotlinx.coroutines.runBlocking
import org.jetbrains.skia.FontWeight

@Composable
fun UserProfileCard(
    email: String,
    onProfileNavigate: () -> Unit,
    onNavigate: () -> Unit,
    userData: UserDetails?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight()
            .padding(10.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icon",
                    modifier = Modifier.size(150.dp),
                    tint = Color(0xFF11009E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    userData?.let { userData ->
                        Text(
                            text = userData.name,
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            ),
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 10.dp,
                                end = 10.dp,
                                bottom = 15.dp
                            )
                        )
                        Divider(
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                        Text(
                            text = userData.email,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                        if (!userData.contactNumber.isNullOrEmpty() && userData.contactNumber != "-") {
                            Text(
                                text = userData.contactNumber,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        if (!userData.role.isNullOrEmpty() && userData.role != "-") {
                            Text(
                                text = userData.role,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Divider(
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                        Button(
                            onClick = onProfileNavigate,
                            modifier = Modifier
                                .padding(top = 20.dp, start = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF11009E)
                            )
                        ) {
                            Text(text = "Update Profile", color = Color.White)
                        }

                    } ?: run { // Use run if userData is null
                        TextButton(
                            onClick = onProfileNavigate,
                            modifier = Modifier.padding(top = 20.dp, start = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF11009E)
                            )
                        ) {
                            Text(text = "Add Profile", color = Color.White)
                        }
                    }
                    TextButton(
                        onClick = onNavigate,
                        modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(
                            text = "Log out", color = Color.White,
                            style = TextStyle(
//                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }
                }
            }
        }
    }
}
