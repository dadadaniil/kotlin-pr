package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke() = repo.refreshTasks()
}
