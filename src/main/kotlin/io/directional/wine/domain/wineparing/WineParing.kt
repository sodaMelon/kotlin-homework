package io.directional.wine.domain.wineparing

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_paring")
data class WineParing(
        val name_korean: String,
        val name_english: String,
        val paring : String
) : BaseEntity()