package com.example.todoapp

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import com.example.todoapp.domain.usecase.GetTasksUseCase
import com.example.todoapp.domain.usecase.RefreshTasksUseCase
import com.example.todoapp.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repo: FakeRepo
    private lateinit var vm: TaskViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = FakeRepo()
        vm = TaskViewModel(
            GetTasksUseCase(repo),
            RefreshTasksUseCase(repo)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadTasksEmitsFromRepository() = runTest(dispatcher) {
        vm.loadTasks()
        advanceUntilIdle()
        repo.emit(listOf(Task(1, "t", false, 1)))
        advanceUntilIdle()
        assertEquals(1, vm.state.value.tasks.size)
    }

    private class FakeRepo : TaskRepository {
        private val flow = MutableStateFlow<List<Task>>(emptyList())
        override fun getTasks(): Flow<List<Task>> = flow
        override suspend fun refreshTasks() {}
        fun emit(list: List<Task>) { flow.value = list }
    }
}
