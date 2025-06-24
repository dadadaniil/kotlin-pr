package com.example.todoapp.domain.model

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean,
    val userId: Int
)
