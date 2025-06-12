package com.example.network.data.mapper

import com.example.network.data.model.CatImageModel
import com.example.network.domain.model.Cat
import com.example.network.domain.model.Breed as DomainBreed

fun CatImageModel.toDomain(): Cat {
    val firstBreed = this.breeds?.firstOrNull()
    
    return Cat(
        id = this.id,
        imageUrl = this.url,
        width = this.width,
        height = this.height,
        breed = firstBreed?.toDomain()
    )
}

fun com.example.network.data.model.Breed.toDomain(): DomainBreed {
    return DomainBreed(
        id = this.id,
        name = this.name,
        temperament = this.temperament,
        description = this.description,
        origin = this.origin,
        lifeSpan = this.lifeSpan
    )
}

fun List<CatImageModel>?.toDomainList(): List<Cat> = this?.map { it.toDomain() } ?: emptyList() 