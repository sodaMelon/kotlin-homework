package io.directional.wine.domain.wineparing

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "wine_paring")
data class WineParing(
        var wineId : Long,
        var paring : String
) : BaseEntity()