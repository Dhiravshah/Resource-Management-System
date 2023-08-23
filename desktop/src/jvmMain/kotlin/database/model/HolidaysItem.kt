package database.model

import kotlinx.serialization.Serializable

@Serializable
data class HolidaysItem(
    val date: String,
    val description: String
)