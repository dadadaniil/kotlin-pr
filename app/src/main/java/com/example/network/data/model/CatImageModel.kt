package com.example.network.data.model

import com.squareup.moshi.Json

data class CatImageModel(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "original_filename")
    val originalFilename: String? = null,
    @Json(name = "sub_id")
    val subId: String? = null,
    val breeds: List<Breed>? = null
)

data class Breed(
    val id: String,
    val name: String,
    val temperament: String? = null,
    val description: String? = null,
    val origin: String? = null,
    @Json(name = "life_span")
    val lifeSpan: String? = null,
    @Json(name = "wikipedia_url")
    val wikipediaUrl: String? = null,
    @Json(name = "weight")
    val weight: Weight? = null
)

data class Weight(
    val imperial: String? = null,
    val metric: String? = null
)