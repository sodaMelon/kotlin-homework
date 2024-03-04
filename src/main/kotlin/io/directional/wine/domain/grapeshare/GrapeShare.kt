package io.directional.wine.domain.grapeshare

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "grape_share")
data class GrapeShare(
        val name_korean: String,
        val name_english: String,
        val share: Double,
        val region_name_korean: String,
        val region_name_english: String
) : BaseEntity()