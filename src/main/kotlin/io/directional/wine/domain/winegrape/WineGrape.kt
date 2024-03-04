package io.directional.wine.domain.winegrape

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_grape")
data class WineGrape(
        val name_korean: String,
        val name_english: String,
        val grape_name_korean: String,
        val grape_name_english: String
) : BaseEntity()