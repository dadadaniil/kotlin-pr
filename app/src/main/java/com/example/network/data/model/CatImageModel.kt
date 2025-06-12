package com.example.network.data.model

import com.google.gson.annotations.SerializedName

data class CatImageModel(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("original_filename")
    val originalFilename: String? = null,
    @SerializedName("sub_id")
    val subId: String? = null,
    val breeds: List<Breed>? = null
)

data class Breed(
    val id: String,
    val name: String,
    val temperament: String? = null,
    val description: String? = null,
    val origin: String? = null,
    @SerializedName("life_span")
    val lifeSpan: String? = null,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String? = null,
    @SerializedName("weight")
    val weight: Weight? = null
)

data class Weight(
    val imperial: String? = null,
    val metric: String? = null
)