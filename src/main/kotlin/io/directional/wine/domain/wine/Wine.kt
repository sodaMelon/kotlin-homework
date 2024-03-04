package io.directional.wine.domain.wine

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "wine")
data class Wine(
        @Id
        val name_korean: String,
        val type: String,
        val name_english: String,
        val alcohol: Double,
        val acidity: Int,
        val body: Int,
        val sweetness: Int,
        val tannin: Int,
        val serving_temperature: Double,
        val score: Double,
        val price: Double,
        val style: String?,
        val grade: String?,
        val importer: String,
        val winery_name_korean: String,
        val winery_name_english: String,
        val region_name_korean: String,
        val region_name_english: String
) : BaseEntity()