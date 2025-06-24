package com.example.todoapp.data.mapper

import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.model.TaskDto
import com.example.todoapp.domain.model.Task

fun TaskDto.toEntity() = TaskEntity(id, title, isDone, userId)
fun TaskDto.toDomain() = Task(id, title, isDone, userId)
fun TaskEntity.toDomain() = Task(id, title, isDone, userId)
