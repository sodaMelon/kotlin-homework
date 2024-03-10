package io.directional.wine.domain.region.dto

import com.querydsl.core.annotations.QueryProjection


data class RegionSearchOneDto @QueryProjection constructor (
        val regionId:Long,
        val regionNameKorean: String,
        val regionNameEnglish: String,
        val regionParentNameKorean: String?,
        val regionParentNameEnglish: String?,
        val grapeId:Long?,
        val grapeNameKorean: String?,
        val grapeNameEnglish: String?,
        val wineId: Long?,
        val wineNameKorean: String?,
        val wineNameEnglish: String?,
        val wineryId: Long?,
        val wineryNameKorean: String?,
        val wineryNameEnglish: String?
)