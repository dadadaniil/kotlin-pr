package com.example.todoapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.ui.viewmodel.TaskViewModel

@Composable
fun TaskListScreen() {
    val vm: TaskViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Tasks") }) }) { padding ->
        Column(Modifier.fillMaxWidth().padding(padding), Arrangement.spacedBy(4.dp)) {
            if (state.isLoading) CircularProgressIndicator()
            LazyColumn {
                items(state.tasks) { task ->
                    ListItem(headlineContent = { Text(task.title, textDecoration = if (task.isDone) TextDecoration.LineThrough else null) }, trailingContent = { Checkbox(checked = task.isDone, onCheckedChange = null) })
                }
            }
            state.error?.let { Text(it) }
        }
    }
}
