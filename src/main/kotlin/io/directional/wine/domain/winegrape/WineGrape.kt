package io.directional.wine.domain.winegrape

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_grape")
data class WineGrape(
        var wineId : Long,
        var grapeId : Long
) : BaseEntity()