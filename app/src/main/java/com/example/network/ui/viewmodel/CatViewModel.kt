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
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CatViewModel"

@HiltViewModel
class CatViewModel @Inject constructor(
    private val getRandomCatsUseCase: GetRandomCatsUseCase,
    private val getCatsByBreedUseCase: GetCatsByBreedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatUiState>(CatUiState.Loading())
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    init {
        loadRandomCats()
    }

    fun loadRandomCats(limit: Int = 10) {
        viewModelScope.launch {
            Log.d(TAG, "Loading initial random cats")
            _uiState.value = CatUiState.Loading()

            getRandomCatsUseCase(limit).fold(
                onSuccess = { cats ->
                    Log.d(TAG, "Loaded ${cats.size} random cats")
                    _uiState.value = CatUiState.Loaded(
                        cats = cats,
                        hasMoreItems = cats.isNotEmpty()
                    )
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading random cats", exception)
                    _uiState.value = CatUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
    
    fun loadMoreCats(limit: Int = 10) {
        val currentState = _uiState.value
        if (currentState !is CatUiState.Loaded || currentState.isLoadingMore || !currentState.hasMoreItems) {
            Log.d(
                TAG,
                "Skipping load more: state=$currentState"
            )
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "Loading more cats")
            _uiState.value = currentState.copy(isLoadingMore = true)

            val currentBreedId = currentState.selectedBreedId

            val result = if (currentBreedId != null) {
                val nextPage = currentState.currentPage + 1
                Log.d(TAG, "Loading more cats for breed: $currentBreedId, page: $nextPage")
                getCatsByBreedUseCase(currentBreedId, limit, nextPage)
            } else {
                Log.d(TAG, "Loading more random cats")
                getRandomCatsUseCase(limit)
            }

            result.fold(
                onSuccess = { newCats ->
                    Log.d(TAG, "Loaded ${newCats.size} more cats")
                    _uiState.value = currentState.copy(
                        cats = currentState.cats + newCats,
                        isLoadingMore = false,
                        currentPage = if (currentBreedId != null) currentState.currentPage + 1 else currentState.currentPage,
                        hasMoreItems = newCats.isNotEmpty()
                    )
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading more cats", exception)
                    _uiState.value = CatUiState.Error(
                        exception.message ?: "Failed to load more cats",
                        selectedBreedId = currentBreedId
                    )
                }
            )
        }
    }
    
    fun loadCatsByBreed(breedId: String, limit: Int = 10) {
        viewModelScope.launch {
            Log.d(TAG, "Loading cats for breed: $breedId")
            _uiState.value = CatUiState.Loading(breedId)

            getCatsByBreedUseCase(breedId, limit).fold(
                onSuccess = { cats ->
                    Log.d(TAG, "Loaded ${cats.size} cats for breed: $breedId")
                    _uiState.value = CatUiState.Loaded(
                        cats = cats,
                        selectedBreedId = breedId,
                        hasMoreItems = cats.isNotEmpty()
                    )
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error loading cats for breed: $breedId", exception)
                    _uiState.value = CatUiState.Error(
                        exception.message ?: "Failed to load cats for breed: $breedId",
                        selectedBreedId = breedId
                    )
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