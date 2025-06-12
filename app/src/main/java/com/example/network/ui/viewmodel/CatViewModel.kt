package com.example.network.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.domain.usecase.GetCatsByBreedUseCase
import com.example.network.domain.usecase.GetRandomCatsUseCase
import com.example.network.ui.state.CatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val getRandomCatsUseCase: GetRandomCatsUseCase,
    private val getCatsByBreedUseCase: GetCatsByBreedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatUiState())
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    init {
        loadRandomCats()
    }

    fun loadRandomCats(limit: Int = 10) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getRandomCatsUseCase(limit).fold(
                onSuccess = { cats ->
                    _uiState.update { 
                        it.copy(
                            cats = cats,
                            isLoading = false,
                            errorMessage = null,
                            selectedBreedId = null
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Unknown error occurred"
                        )
                    }
                }
            )
        }
    }
    
    fun loadCatsByBreed(breedId: String, limit: Int = 10) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getCatsByBreedUseCase(breedId, limit).fold(
                onSuccess = { cats ->
                    _uiState.update {
                        it.copy(
                            cats = cats,
                            isLoading = false,
                            errorMessage = null,
                            selectedBreedId = breedId
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Failed to load cats for breed: $breedId"
                        )
                    }
                }
            )
        }
    }
    
    fun retry() {
        val currentBreedId = uiState.value.selectedBreedId
        if (currentBreedId != null) {
            loadCatsByBreed(currentBreedId)
        } else {
            loadRandomCats()
        }
    }
}