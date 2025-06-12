package com.example.network.domain.repository

import com.example.network.domain.model.Cat

interface CatRepository {
    suspend fun getRandomCats(limit: Int = 10, page: Int = 0): Result<List<Cat>>
    suspend fun getCatsByBreed(breedId: String, limit: Int = 10, page: Int = 0): Result<List<Cat>>
} 