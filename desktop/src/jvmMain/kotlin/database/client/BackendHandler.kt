package database.client

import database.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.prefs.Preferences



val client = HttpClient(CIO) {
    install(HttpTimeout) {
        requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        connectTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
    }
    install(ContentNegotiation) {
        json()
    }
}

//for register user
suspend fun SignUp(email: String, password: String , name: String): String {
    val response = client.request("http://localhost:3000/signUp") {
        method = HttpMethod.Post
        url {
            parameters.append("name",name)
            parameters.append("email", email)
            parameters.append("password", password)

        }
    }
    if (response.status.value == 200) {
        return response.bodyAsText()
    } else {
        return "failure"
    }
}

// to get email password for login
suspend fun SignIn(email: String, password: String): String {
    val response = client.request("http://localhost:3000/signIn") {
        method = HttpMethod.Post
        url {
            parameters.append("email", email)
            parameters.append("password", password)
        }
    }
    return if (response.status.value == 200) {
        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val uid = json["uid"]?.jsonPrimitive?.content
        if (uid != null) {
            storeUserId(uid)
        }
        uid ?: "failure"
    } else {
        "failure"
    }
}



// to get announcement data
suspend fun fetchAnnouncements(): List<AnnouncementItem> {
    val response: HttpResponse = client.get("http://localhost:3000/getAnnouncement")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    return json.decodeFromString(responseBody)
}


//to get holidays list
suspend fun fetchHolidays(): List<HolidaysItem> {
    val response: HttpResponse = client.get("http://localhost:3000/getHolidays")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    return json.decodeFromString(responseBody)
}

// to get attendance list
suspend fun fetchAttendanceRecord(userId: String): List<AttendanceRecords> {
    val response: HttpResponse = client.get("http://localhost:3000/getAttendance?userId=$userId")
    val responseBody = response.bodyAsText()

    val json = Json { ignoreUnknownKeys = true }

    return json.decodeFromString(responseBody)
}

//to add attendance
@OptIn(InternalAPI::class)
suspend fun addAttendance(
    projectName: String,
    date: String,
    totalHours: String,
    description: String
): String {
    val userId = getUserId()
    val response = client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/addAttendance")
        body = FormDataContent(Parameters.build {
            append("userId", userId)
            append("projectName", projectName)
            append("date", date)
            append("totalHours", totalHours)
            append("description", description)
        })
    }
    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}


//update attendance code
@OptIn(InternalAPI::class)
suspend fun updateAttendance(
    id: String,
    projectName: String,
    date: String,
    totalHours: String,
    description: String
): String {
    val userId = getUserId()
    val response = client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/updateAttendance")
        body = FormDataContent(Parameters.build {
            append("id", id)
            append("userId", userId)
            append("projectName", projectName)
            append("date", date)
            append("totalHours", totalHours)
            append("description", description)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}

@OptIn(InternalAPI::class)
suspend fun deleteAttendance(id: String): String {
    val response = client.request {
        method = HttpMethod.Delete
        url("http://localhost:3000/deleteAttendance/$id")
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}


@OptIn(InternalAPI::class)
suspend fun addUserDetails(
   name: String,
   email : String,
   contactNumber: String,
): String {
    val uid = getUserId()
    val response = client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/addProfile")
        body = FormDataContent(Parameters.build {
            append("uid", uid)
            append("name",name)
            append("email", email)
            append("contactNumber", contactNumber)
        })
    }
    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}

suspend fun fetchUserDetails(userId: String): UserDetails? {
    val response: HttpResponse = client.get("http://localhost:3000/getUser/$userId")
    val responseBody = response.bodyAsText()

    return if (response.status.value == 200) {
        val json = Json { ignoreUnknownKeys = true }
        json.decodeFromString<UserDetails>(responseBody)
    } else {
        null
    }
}

@OptIn(InternalAPI::class)
suspend fun updateUserDetails(
    id: String,
    name: String,
    email: String,
    contactNumber: String,
    role: String
): String {
    val userId = getUserId()
    val response = client.request {
        method = HttpMethod.Post
        url("http://localhost:3000/updateUserDetails")
        body = FormDataContent(Parameters.build {
            append("uid",id)
            append("name", name)
            append("email", email)
            append("contactNumber", contactNumber)
            append("role", role)
        })
    }

    return if (response.status.value == 200) {
        response.bodyAsText()
    } else {
        "failure"
    }
}


//to get projects
@OptIn(InternalAPI::class)
suspend fun fetchProjects(): List<ProjectItem> {
    val response: HttpResponse = client.get("http://localhost:3000/getProjects")
    val responseBody = response.bodyAsText()
    val json = Json { ignoreUnknownKeys = true }

    val fetchedProjects = json.decodeFromString<List<ProjectItem>>(responseBody)
    // Assign project numbers
    return fetchedProjects
}

//fun printAnnouncement() {
//    try {
//        val announcements = runBlocking { fetchAnnouncements() }
//        announcements.forEach { announcement ->
//            println("${announcement.description}")
//            println("${announcement.createdOn}")
//        }
//    } catch (e: Exception) {
//        println(e.message)
//    }
//}


//to store userId
fun storeUserId(userId: String) {
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend")
    preferences.put("userId", userId)
    println("Id of logged user is $userId")
    preferences.flush()
}

// to get userId
fun getUserId(): String {
    val preferences = Preferences.userRoot().node("com.example.hrmsbackend")
    return preferences.get("userId", "")
}



