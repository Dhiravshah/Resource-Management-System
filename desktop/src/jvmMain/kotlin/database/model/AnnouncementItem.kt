package database.model

import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementItem(
    val createdOn: String,
    val description: String
)