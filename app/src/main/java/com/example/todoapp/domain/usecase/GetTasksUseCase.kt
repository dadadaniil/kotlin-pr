package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repo: TaskRepository) {
    operator fun invoke() = repo.getTasks()
}
