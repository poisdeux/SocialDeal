package com.example.socialdeal.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onAction: (TopBarAction) -> Unit = {}
) {
    Column {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(R.string.app_name))
            }
        )

        var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

        PrimaryTabRow(selectedTabIndex = selectedDestination) {
            Destination.entries.forEachIndexed { index, destination ->
                Tab(
                    selected = selectedDestination == index,
                    onClick = {
                        selectedDestination = index
                        onAction(TopBarAction.NavigateTo(destination))
                    },
                    text = {
                        Text(
                            text = stringResource(destination.label),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
    }
}

sealed class TopBarAction {
    data class NavigateTo(val destination: Destination) : TopBarAction()
}

enum class Destination(@field:StringRes val label: Int) {
    DEALS(R.string.deals),
    FAVOURITES(R.string.favourites),
    SETTINGS(R.string.settings)
}

