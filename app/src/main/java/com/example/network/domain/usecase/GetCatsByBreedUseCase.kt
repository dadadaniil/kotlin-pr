package com.example.network.domain.usecase

import com.example.network.domain.model.Cat
import com.example.network.domain.repository.CatRepository
import javax.inject.Inject

class GetCatsByBreedUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(breedId: String, limit: Int = 10): Result<List<Cat>> {
        return repository.getCatsByBreed(breedId, limit)
    }
} 