package component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DeleteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    dialogTitle: String,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onCloseRequest = { onDismiss() }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),

                ) {
                Text(
                    text = dialogTitle, color = Color(0xFF11009E),
                    fontSize = 24.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Are You Sure To Delete",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "Yes" , color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onDismiss() },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11009E)
                        )
                    ) {
                        Text(text = "No" ,  color = Color.White)
                    }
                }
            }
        }
    }
}
