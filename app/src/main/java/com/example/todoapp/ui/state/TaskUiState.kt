package com.example.todoapp.ui.state

import com.example.todoapp.domain.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
