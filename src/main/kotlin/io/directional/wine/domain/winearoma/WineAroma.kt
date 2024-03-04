package io.directional.wine.domain.winearoma

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_aroma")
data class WineAroma(
        val name_korean: String,
        val name_english: String,
        val aroma: String
): BaseEntity()