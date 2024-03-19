package io.directional.wine.domain.regionv2.dto

import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.regionv2.RegionV2
import io.directional.wine.domain.winery.Winery


data class RegionV2SearchResponseDto constructor (
        var region : RegionV2,
        var grapes : List<GrapeSimpleDto>?,
        var winery : List<Winery>?
        ){
}