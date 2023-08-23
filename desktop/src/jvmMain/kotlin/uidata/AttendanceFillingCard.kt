package uidata

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttendanceCard(onAttendanceClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(100.dp),
        backgroundColor = Color(0xffffffff),
        elevation = 7.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onAttendanceClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Attendance Filling",
                fontSize = 35.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11009E)
            )
        }
    }
}