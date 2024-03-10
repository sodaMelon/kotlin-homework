package io.directional.wine.domain.wine

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine")
data class Wine(
        var nameKorean: String,
        var type: String,
        var nameEnglish: String,
        var alcohol: Double,
        var acidity: Int,
        var body: Int,
        var sweetness: Int,
        var tannin: Int,
        var servingTemperature: Double,
        var score: Double,
        var price: Double,
        var style: String?,
        var grade: String?,
        var importer: String,
        var wineryId: Long,
        var regionId: Long,
) : BaseEntity()