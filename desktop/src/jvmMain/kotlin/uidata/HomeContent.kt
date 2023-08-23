package uidata

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import component.AttendanceRecords
//import component.AnnouncementItem
import database.model.AttendanceRecords
import database.model.AnnouncementItem
import androidx.compose.material.*
import androidx.compose.runtime.*
import database.model.HolidaysItem
import view.calendar
import java.time.LocalDate
import com.
//import com.example.calendarlibrary.calendarlibrary.calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    onAttendanceClick: () -> Unit,
    attendancelist: List<AttendanceRecords>,
    announcements: List<AnnouncementItem>,
    holidays: List<HolidaysItem>
) {



    Column(modifier = Modifier.padding(10.dp)) {
        AttendanceCard(onAttendanceClick = onAttendanceClick)
        Row {
            Column {
                Row {
                    Card(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(0.4f)
                            .padding(top = 10.dp),
                        backgroundColor = Color.White,
                        elevation = 7.dp,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                        }
                    }

                    HolidaysCard(holidays = holidays)
                }
                AttendanceRecordsCard(attendancelist = attendancelist)
            }
            AnnouncementCard(announcements = announcements)
        }

    }
}









