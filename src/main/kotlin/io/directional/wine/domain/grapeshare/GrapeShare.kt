package io.directional.wine.domain.grapeshare

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "grape_share")
data class GrapeShare(
        var share: Double,
        var regionId : Long,
        var grapeId : Long
) : BaseEntity()