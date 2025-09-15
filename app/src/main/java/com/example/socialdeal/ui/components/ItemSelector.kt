package com.example.socialdeal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.theme.SocialDealTheme

@Composable
fun ItemSelector(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.wrapContentWidth(Alignment.Start),
            value = selectedItem,
            label = { Text(label) },
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        painter = painterResource(if (expanded) R.drawable.outline_arrow_drop_up_24 else R.drawable.outline_arrow_drop_down_24),
                        contentDescription = if (expanded) "Collapse list of options" else "Expand list of options"
                    )
                }
            }
        )

        DropdownMenu(
            modifier = Modifier.align(Alignment.End),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemSelectorPreview() {
    var selectedItem by remember { mutableStateOf("Option 1") }
    SocialDealTheme {
        ItemSelector(
            label = "Currency",
            selectedItem = selectedItem,
            items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        ) {
            selectedItem = it
        }
    }
}

