package io.directional.wine.domain.grapeshare

import io.directional.wine.domain.BaseEntity
import io.directional.wine.domain.grape.Grape
import io.directional.wine.domain.regionv2.RegionV2
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "grape_share")
data class GrapeShare(
        var share: Double,
        @ManyToOne
        @JoinColumn(name="region_id")
        var region : RegionV2,
        @ManyToOne
        @JoinColumn(name="grape_id")
        var grape : Grape
) : BaseEntity()