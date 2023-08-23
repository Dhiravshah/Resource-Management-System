package uidata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.HolidaysItem

@Composable
fun HolidaysCard(holidays: List<HolidaysItem>){

    Card(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(0.5f)
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        backgroundColor = Color.White,
        elevation = 7.dp
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Holidays",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF11009E)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(holidays) { holiday ->
                println(holiday.description)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    backgroundColor = Color.White,
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = holiday.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = holiday.date.toString(),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
        }
    }
}
