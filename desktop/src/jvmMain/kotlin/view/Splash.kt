// Splash.kt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.delay
import java.io.File

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val splashFinished = remember { mutableStateOf(false) }

    // Simulate delay
    LaunchedEffect(Unit) {
        delay(2000) // Adjust the duration as needed

        splashFinished.value = true
        onTimeout()
    }

    if (!splashFinished.value) {

        // Your splash screen content

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "RESOURCE",
                fontSize = 30.sp,
                style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif),
                color = Color(0xFF11009E),
            )
            Text(
                text = "MANAGEMENT",
                fontSize = 30.sp,
                style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif),
                color = Color(0xFF11009E),

                )
            Text(
                text = "SYSTEM",
                fontSize = 30.sp,
                style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif),
                color = Color(0xFF11009E),

                )
        }
//        AsyncImage(
//            model = ,
//            modifier = Modifier.size(72.dp),
//            contentDescription = null,
//        )
//        Image(
//            painter = painterResource("https://imges.unsplash.com/photo-1605857840732-188f2f08cb31?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTQ5fHxwb3J0cmFpdHxlbnwwfDJ8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"), // Replace with your splash screen image
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds
//        )
    }
}
