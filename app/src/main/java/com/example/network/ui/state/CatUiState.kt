package com.example.network.ui.state

import com.example.network.domain.model.Cat

data class CatUiState(
    val cats: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val selectedBreedId: String? = null,
    val currentPage: Int = 0,
    val hasMoreItems: Boolean = true
)