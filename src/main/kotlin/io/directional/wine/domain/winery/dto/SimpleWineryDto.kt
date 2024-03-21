package io.directional.wine.domain.winery.dto

import io.directional.wine.domain.wine.dto.SimpleWineDto

class SimpleWineryDto(
        var wineryId: Long?,
        var wineryNameKorean: String,
        var wineryNameEnglish: String,
        var wineryRegionId: Long,
        val wines: List<SimpleWineDto>
)
