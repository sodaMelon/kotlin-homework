package io.directional.wine.domain.regionv2.dto

import io.directional.wine.domain.region.dto.RegionNameSort

data class RegionV2SearchRequestDto(
        val parentRegionId: Long?,
        val sort: RegionNameSort,
        val key: String
) {
}