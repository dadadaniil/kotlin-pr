package com.example.network.domain.repository

import com.example.network.domain.model.Cat

interface CatRepository {
    suspend fun getRandomCats(limit: Int): Result<List<Cat>>
    suspend fun getCatsByBreed(breedId: String, limit: Int): Result<List<Cat>>
} 