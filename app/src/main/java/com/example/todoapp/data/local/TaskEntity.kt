package com.example.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val isDone: Boolean,
    val userId: Int
)
