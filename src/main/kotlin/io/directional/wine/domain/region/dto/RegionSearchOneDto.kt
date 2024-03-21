package io.directional.wine.domain.region.dto

import io.directional.wine.domain.region.Region
import io.directional.wine.domain.winery.dto.SimpleWineryDto


class RegionSearchOneDto constructor(
        val region: Region?,
        val grapes: List<GrapeSimpleDto>,
        val winery: List<SimpleWineryDto>
)