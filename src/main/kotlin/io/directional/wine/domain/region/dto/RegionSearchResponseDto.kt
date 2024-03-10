package io.directional.wine.domain.region.dto

import io.directional.wine.domain.region.Region
import io.directional.wine.domain.winery.Winery


data class RegionSearchResponseDto constructor (
        var region : Region,
        var grapes : List<GrapeSimpleDto>?,
        var winery : List<Winery>?
        ){
}