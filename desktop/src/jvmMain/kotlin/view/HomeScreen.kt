package view

import androidx.compose.foundation.layout.*

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import database.client.*
import database.model.AnnouncementItem
import database.model.AttendanceRecords
import database.model.HolidaysItem
import database.model.UserDetails
import uidata.HomeContent
import uidata.UserProfileCard

//import database.client.getAnnouncements

@Composable
fun HomeScreen(
    email: String,
    onProfieNavigate: () -> Unit,
    onNavigate: () -> Unit,
    onAttendanceClick: () -> Unit,

) {
    var announcements by remember { mutableStateOf<List<AnnouncementItem>>(emptyList()) }
    var announcementsLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        try {
            val fetchedAnnouncements = fetchAnnouncements()
            announcements = fetchedAnnouncements
        } catch (e: Exception) {
            // Handle the error
        } finally {
            announcementsLoading = false
        }
    }

    var holidays by remember { mutableStateOf<List<HolidaysItem>>(emptyList()) }
    var holidaysLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val fetchedHolidays = fetchHolidays()
            holidays = fetchedHolidays
        } catch (e: Exception) {
            // Handle the error
        } finally {
            holidaysLoading = false
        }
    }

    var attendancelist by remember { mutableStateOf<List<AttendanceRecords>>(emptyList()) }
    var attendanceLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        try {
            val fetchedAttendance = fetchAttendanceRecord(userId = getUserId())
            attendancelist = fetchedAttendance
        } catch (e: Exception) {
            // Handle the error
        } finally {
            attendanceLoading = false
        }
    }


    var userDetails by remember { mutableStateOf<UserDetails?>(null) }
    var userLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val fetchuserDetails = fetchUserDetails(userId = getUserId())
            userDetails = fetchuserDetails
        } catch (e: Exception) {
            // Handle the error
        } finally {
            userLoading = false
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {


        if (announcementsLoading || holidaysLoading || attendanceLoading || userLoading) {
            CircularProgressIndicator(color = Color(0xFF11009E))
        } else {
            UserProfileCard(
                email = email,
                onProfileNavigate = onProfieNavigate,
                onNavigate = onNavigate,
                userData = userDetails
            )
            HomeContent(
                onAttendanceClick = onAttendanceClick,
                attendancelist = attendancelist,
                announcements = announcements,
                holidays = holidays
            )
        }
    }
}


