package com.remindermatrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.remindermatrix.ui.detail.TaskDetailScreen
import com.remindermatrix.ui.inbox.InboxScreen
import com.remindermatrix.ui.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "inbox") {
                composable("inbox") {
                    InboxScreen(
                        onTaskClick = { taskId ->
                            navController.navigate("task/$taskId")
                        },
                        onSettingsClick = {
                            navController.navigate("settings")
                        }
                    )
                }
                composable("settings") {
                    SettingsScreen()
                }
                composable(
                    "task/{taskId}",
                    arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                ) { backStackEntry ->
                    TaskDetailScreen(
                        taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                    )
                }
            }
        }
    }
}
