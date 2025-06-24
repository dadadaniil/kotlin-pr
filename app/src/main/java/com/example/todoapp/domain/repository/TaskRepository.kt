package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun refreshTasks()
}
