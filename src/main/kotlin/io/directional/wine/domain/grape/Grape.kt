package io.directional.wine.domain.grape

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "grape")
data class Grape(
        val name_korean: String,
        val name_english: String,
        val acidity: Int,
        val body: Int,
        val sweetness: Int,
        val tannin: Int
) : BaseEntity()