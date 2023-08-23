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
import database.client.addAttendance
import kotlinx.coroutines.runBlocking

import view.homeScreen.DropDownContent


@Composable
fun AttendanceFillingScreen(onNavigate: () -> Unit) {

    var selectedProject by remember { mutableStateOf("") }
    var inputDate by remember { mutableStateOf("") }
    var inputHours by remember { mutableStateOf("") }
    var inputDesc by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isTextFieldClicked by remember { mutableStateOf(false) }


    Card(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp), elevation = 20.dp) {
        Column(

            modifier = Modifier.fillMaxWidth().padding(20.dp),
        ) {
            Row {
                Text(
                    text = "Attendance Filling",
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
            Text(
                text = "User : Dhirav Shah      ",
                modifier = Modifier.padding(bottom = 36.dp),
                color = MaterialTheme.colors.onSurface,
                fontSize = 20.sp,
                style = TextStyle(fontFamily = FontFamily.Serif)
            )
            DropDownContent("", onProjectSelected = { project ->
                selectedProject = project
            })
            Row() {

                OutlinedTextField(
                    value = inputDate,
                    onValueChange = { inputDate = it },
                    label = {
                        Text(
                            "Date",
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
                    value = inputHours,
                    onValueChange = { inputHours = it },
                    label = {
                        Text(
                            "Total Hours",
                            color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                0xFF11009E
                            )
                        )
                    },
                    modifier = Modifier.padding(bottom = 8.dp, end = 10.dp),
                    isError = inputHours.isNotEmpty() && runCatching { inputHours.toFloat() }.isFailure,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                            0xFF11009E
                        )
                    )

                )
            }
            OutlinedTextField(
                value = inputDesc,
                onValueChange = { inputDesc = it },
                label = {
                    Text(
                        "Description",
                        color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                            0xFF11009E
                        )
                    )
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 10.dp).height(100.dp)
                    .fillMaxWidth(),
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
                    if (selectedProject.isNotEmpty() && inputDate.isNotEmpty() && inputHours.isNotEmpty() && inputDesc.isNotEmpty()) {
                        val projectName = selectedProject
                        val date = inputDate
                        val totalHours = inputHours.toFloatOrNull()
                        val description = inputDesc
                        val dateFormat = "\\d{2}/\\d{2}/\\d{4}".toRegex()
                        val isValidDate = date.matches(dateFormat)

                        if (isValidDate && totalHours != null) {
                            val result =
                                runBlocking {
                                    addAttendance(
                                        projectName,
                                        date,
                                        totalHours.toString(),
                                        description
                                    )
                                }
                            println(result)
                            onNavigate()
                        } else if (!isValidDate) {
                            showErrorMessage = true
                            errorMessage = "Please enter a valid date in the format MM/dd/yyyy"
                        } else {
                            showErrorMessage = true
                            errorMessage = "Please enter a valid number for Total Hours"
                        }
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