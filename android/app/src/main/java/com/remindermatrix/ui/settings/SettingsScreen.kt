package com.remindermatrix.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var weeklyDigestHour by remember { mutableStateOf(viewModel.getWeeklyDigestHour().toFloat()) }
    // Add state for daily digest hour as well
    // var dailyDigestHour by remember { mutableStateOf(viewModel.getDailyDigestHour().toFloat()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Weekly Digest Time: ${weeklyDigestHour.toInt()}:00")
            Slider(
                value = weeklyDigestHour,
                onValueChange = { weeklyDigestHour = it },
                valueRange = 0f..23f,
                steps = 22,
                onValueChangeFinished = {
                    viewModel.setWeeklyDigestHour(weeklyDigestHour.toInt())
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text("Note: App restart is required for digest time changes to take effect.")
        }
    }
}
