import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import database.model.AttendanceRecords
import database.model.UserDetails
import view.*
import java.util.prefs.Preferences

fun main() =
    singleWindowApplication(
        title = "Resource Management System",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        val currentScreen = remember { mutableStateOf("Splash") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var storedEmail by remember { mutableStateOf("") }
        val prefrences = Preferences.userRoot().node("com.example.project")
        storedEmail = prefrences.get("userEmail", "")
        val attendanceRecords by remember { mutableStateOf(listOf<AttendanceRecords>()) }

        when (currentScreen.value) {
            "Splash" -> {
                SplashScreen(onTimeout = {
                    if (storedEmail.isNotEmpty()) {
                        currentScreen.value = "Home"
                    } else currentScreen.value = "Login"
                })
            }
            "Login" -> LogInScreen(
                onButtonSignUpClick = { currentScreen.value = "Signup" },
                onButtonSubmitClick = {
                    currentScreen.value = "Home"
                }
            )
            "Home" -> HomeScreen(
                email = email,
                onNavigate = {
                    logout()
                    currentScreen.value = "Login"
                },
                onProfieNavigate = {currentScreen.value = "UserProfile"},
                onAttendanceClick = { currentScreen.value = "Attendance" },
            )
            "Signup" -> SignupScreen(onNavigate = { currentScreen.value = "Login" } )
            "Attendance" -> AttendanceFillingScreen(onNavigate = { currentScreen.value = "Home" })
            "UserProfile" ->  UserProfileScreen(onNavigate = {currentScreen.value = "Home"} )
            "Calendar" -> calendar()

        }

    }

fun logout() {
    val prefrences = Preferences.userRoot().node("com.example.project")
    prefrences.remove("userEmail")
}