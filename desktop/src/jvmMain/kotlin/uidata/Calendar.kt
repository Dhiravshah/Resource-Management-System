package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.AttendanceRecords
import uidata.AttendanceRecordsCard
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun calendar() {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val yearMonth = remember { mutableStateOf(YearMonth.now()) }
    val clickedDateText = remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarHeader(yearMonth.value,
            onPreviousMonthClicked = {
                yearMonth.value = yearMonth.value.minusMonths(1)
            }, // Update yearMonth to previous month
            onNextMonthClicked = {
                yearMonth.value = yearMonth.value.plusMonths(1)
            } // Update yearMonth to next month
        )

        CalendarGrid(
            yearMonth.value,
            selectedDate.value,
            onDateSelected = { date ->
                selectedDate.value = date
                clickedDateText.value = date.format(dateFormatter)
                println("New Date: ${clickedDateText.value}")
            }
        )
    }
}


@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    onPreviousMonthClicked: () -> Unit,
    onNextMonthClicked: () -> Unit
) {
    val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onPreviousMonthClicked() },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        Text(
            text = yearMonth.format(monthYearFormatter),
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF11009E)
        )
        IconButton(
            onClick = { onNextMonthClicked() },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

    val gridCells = mutableListOf<LocalDate?>()

    val startDate = firstDayOfMonth.minusDays(startDayOfWeek.toLong())

    gridCells.addAll(
        List(startDayOfWeek) { index ->
            startDate.plusDays(index.toLong())
        }
    )

    gridCells.addAll(
        List(daysInMonth) { index ->
            startDate.plusDays(startDayOfWeek.toLong() + index.toLong())
        }
    )

    val totalGridCells = 42 // 7 days * 6 rows

    val remainingEmptyCells = totalGridCells - gridCells.size
    gridCells.addAll(List(remainingEmptyCells) { null })

    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        // Add days of the week
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val daysOfWeek = listOf( "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            for (dayOfWeek in daysOfWeek) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfWeek,
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Add calendar cells
        val rows = totalGridCells / 7
        for (row in 0 until rows) {
            Column(modifier = Modifier.padding(vertical = 2.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(26.dp),// Add horizontal spacing between cells
                ) {
                    for (column in 0 until 7) {
                        val index = row * 7 + column
                        val date = gridCells[index]
                        if (date != null) {
                            CalendarCell(date, selectedDate, onDateSelected)
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                // Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun CalendarCell(
    date: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val isCurrentMonth = date.monthValue == selectedDate.monthValue
    val isSelected = date == selectedDate

    val backgroundColor = if (isSelected) {
        Color(0xFF11009E)
    } else if (isCurrentMonth) {
        MaterialTheme.colors.background
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    }

    val textColor = if (isSelected) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onSurface
    }

    Box(
        modifier = Modifier
            .size(30.dp) // Adjust the size of the cell
            .border(
                border = BorderStroke(1.dp, Color(0xFF11009E)),
                shape = MaterialTheme.shapes.small
            )
            .background(color = backgroundColor)
            .clickable { onDateSelected(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.caption.copy(color = textColor)
        )
    }
}