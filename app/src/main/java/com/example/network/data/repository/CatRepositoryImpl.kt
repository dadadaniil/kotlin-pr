package com.example.network.data.repository

import com.example.network.data.mapper.toDomainList
import com.example.network.data.network.CatApiService
import com.example.network.domain.model.Cat
import com.example.network.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val apiService: CatApiService
) : CatRepository {
    
    override suspend fun getRandomCats(limit: Int): Result<List<Cat>> {
        return withContext(Dispatchers.IO) {
            try {
                val cats = apiService.getRandomCats(limit = limit)
                Result.success(cats.toDomainList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    override suspend fun getCatsByBreed(breedId: String, limit: Int): Result<List<Cat>> {
        return withContext(Dispatchers.IO) {
            try {
                val cats = apiService.getCatsByBreed(breedId = breedId, limit = limit)
                Result.success(cats.toDomainList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 