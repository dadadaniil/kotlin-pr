package com.example.network.ui.state

import com.example.network.domain.model.Cat

data class CatUiState(
    val cats: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedBreedId: String? = null
)