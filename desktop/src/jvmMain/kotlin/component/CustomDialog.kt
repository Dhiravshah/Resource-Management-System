package component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import database.model.AttendanceRecords
import view.homeScreen.DropDownContent

@Composable
fun CustomDialog(
    selectedAttendance: AttendanceRecords?,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    onConfirm: (AttendanceRecords, String, String, String, String, String) -> Unit
) {
    var scrollState = rememberScrollState()
    var isTextFieldClicked by remember { mutableStateOf(false) }

    //var selectedProject by remember { mutableStateOf("") }


    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            var inputId by remember { mutableStateOf(selectedAttendance?.id ?: "") }
            var inputDesc by remember { mutableStateOf(selectedAttendance?.description ?: "") }
            var inputDate by remember { mutableStateOf(selectedAttendance?.date ?: "") }
            var inputHours by remember { mutableStateOf(selectedAttendance?.totalHours ?: "") }
            var selectedProject by remember {
                mutableStateOf(
                    selectedAttendance?.projectName ?: ""
                )
            }
            var showErrorMessage by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(16.dp)
                    .verticalScroll(state = scrollState, enabled = true)
            ) {
                Text(
                    text = dialogTitle, color = Color(0xFF11009E),
                    fontSize = 24.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                )
                Spacer(modifier = Modifier.height(16.dp))
                DropDownContent(
                    selectedValue = selectedProject,
                    onProjectSelected = { project ->
                        selectedProject = project
                    }
                )
                OutlinedTextField(
                    value = inputDate,
                    onValueChange = { inputDate = it },
                    label = {
                        Text(
                            text = "Date",
                            color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputHours,
                    onValueChange = { inputHours = it },
                    label = {
                        Text(
                            text = "Total Hours",
                            color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                                0xFF11009E
                            )
                        )
                    },
                    isError = inputHours.isNotEmpty() && runCatching { inputHours.toFloat() }.isFailure,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                            0xFF11009E
                        )
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputDesc,
                    onValueChange = { inputDesc = it },
                    label = {
                        Text(
                            text = "Description",
                            color = if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
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
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.Red,
                        style = MaterialTheme.typography.caption
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            // println(selectedProject)
                            if (inputDate.isNotEmpty() && inputHours.isNotEmpty()) {
                                val date = inputDate
                                val totalHours = inputHours.toFloatOrNull()
                                val dateFormat = "\\d{2}/\\d{2}/\\d{4}".toRegex()
                                val isValidDate = date.matches(dateFormat)

                                if (isValidDate && totalHours != null) {
                                    // Pass the updated attendance and text
                                    onConfirm(
                                        selectedAttendance ?: AttendanceRecords(
                                            id = inputId,
                                            projectName = selectedProject,
                                            date = inputDate,
                                            totalHours = inputHours,
                                            description = inputDesc,
                                        ),
                                        inputId,
                                        selectedProject,
                                        inputDate,
                                        inputHours,
                                        inputDesc
                                    )
                                    onDismiss()
                                } else if (!isValidDate) {
                                    showErrorMessage = true
                                    errorMessage =
                                        "Please enter a valid date in the format MM/dd/yyyy"
                                } else {
                                    showErrorMessage = true
                                    errorMessage = "Please enter a valid number for Total Hours"
                                }
                            } else {
                                showErrorMessage = true
                                errorMessage = "Please fill in all fields"
                            }
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Update", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onDismiss() },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                }
            }
        }
    }
}

