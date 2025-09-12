package com.example.socialdeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.socialdeal.ui.MainActivityViewModel
import com.example.socialdeal.ui.components.TopBar
import com.example.socialdeal.ui.components.TopBarAction
import com.example.socialdeal.ui.screens.MainScreen
import com.example.socialdeal.ui.screens.MainScreenAction
import com.example.socialdeal.ui.theme.SocialDealTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels { MainActivityViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()

            SocialDealTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar(
                        onAction = {
                            when (it) {
                                is TopBarAction.NavigateTo -> viewModel.navigateTo(it.destination)
                            }
                        }
                    ) },
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        mainScreenState = uiState,
                        onAction = {
                            when (it) {
                                is MainScreenAction.ShowDealDetail -> viewModel.showDealDetail(it.deal)
                            }
                        }
                    )
                }
            }
        }
    }
}