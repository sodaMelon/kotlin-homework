package io.directional.wine.domain.winery

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "winery")
data class Winery(
        val nameKorean: String,
        val nameEnglish: String,
        var regionId : Long
) : BaseEntity();