package io.directional.wine.domain.winearoma

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_aroma")
data class WineAroma(
        var wineId: Long,
        var aroma: String
): BaseEntity()