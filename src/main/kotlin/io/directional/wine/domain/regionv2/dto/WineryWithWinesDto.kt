package io.directional.wine.domain.region.dto

import com.querydsl.core.annotations.QueryProjection

class WineryWithWinesDto @QueryProjection constructor(
        var wineryId : Long,
        var wineryNameKorean: String,
        var wineryNameEnglish: String,
        var wineryRegionId: Long,
        var wineId : Long?,
        var wineNameKorean: String?,
        var wineNameEnglish: String?,
        var wineWineryId : Long?,
        var wineRegionId : Long?,
)


