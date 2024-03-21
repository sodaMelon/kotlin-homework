package io.directional.wine.domain.regionv2.dto

import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.regionv2.RegionV2
import io.directional.wine.domain.winery.dto.SimpleWineryDto


class RegionV2SearchOneDto constructor(
        val region: RegionV2?,
        val grapes: List<GrapeSimpleDto>,
        val winery: List<SimpleWineryDto>
)