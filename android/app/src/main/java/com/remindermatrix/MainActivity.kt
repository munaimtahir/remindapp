package com.remindermatrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}

@Composable
fun AppRoot() {
    var title by remember { mutableStateOf("") }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Reminder‑Matrix") }) },
        content = { padding ->
            Column(Modifier.padding(padding).padding(16.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Quick task") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Row {
                    Button(onClick = { /* TODO: save + schedule */ }) { Text("Add") }
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(onClick = { title = "" }) { Text("Clear") }
                }
                Spacer(Modifier.height(16.dp))
                Text("Inbox (stub list)", style = MaterialTheme.typography.titleMedium)
            }
        }
    )
}
