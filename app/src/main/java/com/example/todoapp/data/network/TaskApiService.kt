package com.example.todoapp.data.network

import com.example.todoapp.data.model.TaskDto
import retrofit2.http.GET

interface TaskApiService {
    @GET("todos")
    suspend fun getTasks(): List<TaskDto>
}
