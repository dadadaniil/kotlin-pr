package com.example.network.domain.usecase

import com.example.network.domain.model.Cat
import com.example.network.domain.repository.CatRepository
import javax.inject.Inject

class GetRandomCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(limit: Int = 10, page: Int = 0): Result<List<Cat>> {
        return repository.getRandomCats(limit, page)
    }
} 