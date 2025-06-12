package com.example.network.data.repositorty

import com.example.network.data.model.CatImageModel
import com.example.network.data.network.CatApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepository(
    private val apiService: CatApiService
) {
    suspend fun getRandomCats(limit: Int = 10): Result<List<CatImageModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val cats = apiService.getRandomCats(limit = limit)
                Result.success(cats)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getCatsByBreed(breedId: String, limit: Int = 10): Result<List<CatImageModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val cats = apiService.getCatsByBreed(breedId = breedId, limit = limit)
                Result.success(cats)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}