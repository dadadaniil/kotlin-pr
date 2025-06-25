package com.example.network.ui.state

import com.example.network.domain.model.Cat

sealed interface CatUiState {
    val selectedBreedId: String?

    data class Loading(
        override val selectedBreedId: String? = null
    ) : CatUiState

    data class Loaded(
        val cats: List<Cat>,
        val isLoadingMore: Boolean = false,
        override val selectedBreedId: String? = null,
        val currentPage: Int = 0,
        val hasMoreItems: Boolean = true
    ) : CatUiState

    data class Error(
        val message: String,
        override val selectedBreedId: String? = null
    ) : CatUiState
}
