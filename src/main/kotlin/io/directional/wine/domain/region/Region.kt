package io.directional.wine.domain.region

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "region")
data class Region(
        val name_korean: String,
        val name_english: String,
        val parent_name_korean: String?,
        val parent_name_english: String?
) : BaseEntity()