package io.directional.wine.domain.region.dto

data class RegionSearchRequestDto(
        val parentRegionId: Long?,
        val sort: RegionNameSort,
        val key: String
)