package com.example.network.ui.viewmodel

import android.util.Log
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

private const val TAG = "CatViewModel"

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
            Log.d(TAG, "Loading initial random cats")
            _uiState.update { it.copy(isLoading = true, errorMessage = null, currentPage = 0) }

            getRandomCatsUseCase(limit).fold(
                onSuccess = { cats ->
                    Log.d(TAG, "Loaded ${cats.size} random cats")
                    _uiState.update { 
                        it.copy(
                            cats = cats,
                            isLoading = false,
                            errorMessage = null,
                            selectedBreedId = null,
                            hasMoreItems = cats.isNotEmpty()
                        )
                    }
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading random cats", exception)
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
    
    fun loadMoreCats(limit: Int = 10) {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMoreItems) {
            Log.d(TAG, "Skipping load more: isLoadingMore=${_uiState.value.isLoadingMore}, hasMoreItems=${_uiState.value.hasMoreItems}")
            return
        }
        
        viewModelScope.launch {
            Log.d(TAG, "Loading more cats")
            _uiState.update { it.copy(isLoadingMore = true) }
            
            val currentBreedId = _uiState.value.selectedBreedId
            
            val result = if (currentBreedId != null) {
                // For breed-specific cats, use pagination
                val currentPage = _uiState.value.currentPage + 1
                Log.d(TAG, "Loading more cats for breed: $currentBreedId, page: $currentPage")
                getCatsByBreedUseCase(currentBreedId, limit, currentPage)
            } else {
                // For random cats, just load a new batch
                Log.d(TAG, "Loading more random cats")
                getRandomCatsUseCase(limit)
            }
            
            result.fold(
                onSuccess = { newCats ->
                    Log.d(TAG, "Loaded ${newCats.size} more cats")
                    _uiState.update {
                        it.copy(
                            cats = it.cats + newCats,
                            isLoadingMore = false,
                            currentPage = if (currentBreedId != null) it.currentPage + 1 else it.currentPage,
                            hasMoreItems = newCats.isNotEmpty()
                        )
                    }
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading more cats", exception)
                    _uiState.update {
                        it.copy(
                            isLoadingMore = false,
                            errorMessage = exception.message ?: "Failed to load more cats"
                        )
                    }
                }
            )
        }
    }
    
    fun loadCatsByBreed(breedId: String, limit: Int = 10) {
        viewModelScope.launch {
            Log.d(TAG, "Loading cats for breed: $breedId")
            _uiState.update { it.copy(isLoading = true, errorMessage = null, currentPage = 0) }
            
            getCatsByBreedUseCase(breedId, limit).fold(
                onSuccess = { cats ->
                    Log.d(TAG, "Loaded ${cats.size} cats for breed: $breedId")
                    _uiState.update {
                        it.copy(
                            cats = cats,
                            isLoading = false,
                            errorMessage = null,
                            selectedBreedId = breedId,
                            hasMoreItems = cats.isNotEmpty()
                        )
                    }
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading cats for breed: $breedId", exception)
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