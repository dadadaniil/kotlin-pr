package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.data.mapper.toEntity
import com.example.todoapp.data.network.TaskApiService
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApiService,
    private val dao: TaskDao
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> =
        dao.getTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshTasks() {
        val remote = api.getTasks()
        dao.clearTasks()
        dao.insertTasks(remote.map { it.toEntity() })
    }
}
