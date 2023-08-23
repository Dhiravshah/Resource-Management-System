package database.model

import kotlinx.serialization.Serializable


@Serializable
data class UserDetails(
    val name: String,
    val email: String,
    val contactNumber: String,
    val role: String,
)