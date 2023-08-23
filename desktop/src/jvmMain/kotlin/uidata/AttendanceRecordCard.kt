package uidata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.CustomDialog
import component.DeleteDialog
import database.client.deleteAttendance
import database.client.updateAttendance
import database.model.AttendanceRecords
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AttendanceRecordsCard(attendancelist: List<AttendanceRecords>) {

    var selectedAttendance by remember { mutableStateOf<AttendanceRecords?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }



    //function for showing delete dialog
    fun onDeleteClick(attendance: AttendanceRecords) {
        selectedAttendance = attendance
        showDialog = true
    }

    //for delete data
    DeleteDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        dialogTitle = "Delete Data",
        onConfirm = {
            selectedAttendance?.let { attendance ->
                val result = runBlocking {
                    deleteAttendance(
                        id = attendance.id
                    )
                }
                selectedAttendance = null
            }
            showDeleteDialog = false
        }
    )

    //fun for showing edit dialog
    CustomDialog(
        selectedAttendance = selectedAttendance,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        dialogTitle = "Edit Data",
        onConfirm = { attendance, id, projectName, date, totalHours, description ->

            val result = runBlocking {
                updateAttendance(
                    id, projectName, date, totalHours, description
                )
            }
        }
    )

    Card(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.7f)
            .padding(top = 10.dp, end = 10.dp),
        backgroundColor = Color.White,
        elevation = 7.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 10.dp),
                backgroundColor = Color.White,
                elevation = 7.dp
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Text(
                            text = "Attendance Records",
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF11009E)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(36.dp))
                            Text(
                                text = "Project",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(26.dp))
                            Text(
                                text = "Date",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Total Hours",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1.1f)
                            )
                            Spacer(modifier = Modifier.width(26.dp))
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(2f).padding(end = 16.dp)

                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Actions",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    val sortedAttendancedata = attendancelist.sortedByDescending { it.date }

                    items(sortedAttendancedata) { attendance ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(36.dp))
                            Text(
                                text = attendance.projectName,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(26.dp))
                            Text(
                                text = attendance.date,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = attendance.totalHours.toString(),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .weight(1.1f)
                                    .padding(horizontal = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = attendance.description,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(end = 20.dp)
                            )
                            Row(modifier = Modifier.weight(1f)) {
                                Box(Modifier.padding(horizontal = 4.dp)) {
                                    IconButton(
                                        onClick = {// Set the selected attendance record
                                            selectedAttendance = attendance
                                            // Show the dialog
                                            showDialog = true
                                            println("Attendace id of the data is : ${attendance.id}")
                                        },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = Color(0xFF11009E)
                                        )
                                    }
                                }

                                Box(Modifier.padding(start = 4.dp)) {
                                    IconButton(
                                        onClick = {
                                            selectedAttendance = attendance
                                            showDeleteDialog = true
                                        },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color(0xFF11009E)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}