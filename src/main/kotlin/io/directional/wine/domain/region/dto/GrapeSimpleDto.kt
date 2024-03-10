package io.directional.wine.domain.region.dto

import com.querydsl.core.annotations.QueryProjection

data class GrapeSimpleDto @QueryProjection constructor(
        var id : Long,
        var nameKorean: String,
        var nameEnglish: String,
        var regionId : Long
) {
}