package database.model

import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRecords(
    val id:String,
    val date: String,
    val description: String,
    val projectName: String,
    val totalHours: String
)