package view.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun DropDownContent(selectedValue: String, onProjectSelected: (String) -> Unit) {
    var mExpanded by remember { mutableStateOf(false) }
    val mDevices = listOf(
        "Training Project",
        "Internship Project",
        "Live Project",
        "Resource Management",
        "Device Management"
    )
    var mSelectedText by remember { mutableStateOf(selectedValue) }
    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var isTextFieldClicked by remember { mutableStateOf(false) }
    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isTextFieldClicked) Color(0xFF11009E) else Color(
                    0xFF11009E
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    "Select Your Project", color =
                    if (isTextFieldClicked) MaterialTheme.colors.primary else Color(
                        0xFF11009E
                    )
                )
            },
            trailingIcon = {
                Icon(
                    icon,
                    "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded }
                )
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = {
                mExpanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            mDevices.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                    onProjectSelected(label)
                }) {
                    Text(text = label)
                }
            }
        }
    }

}
