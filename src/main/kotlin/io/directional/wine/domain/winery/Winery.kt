package io.directional.wine.domain.winery

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "winery")
data class Winery(
        val name_korean: String,
        val name_english: String,
        val region_name_korean: String,
        val region_name_english: String
) : BaseEntity();