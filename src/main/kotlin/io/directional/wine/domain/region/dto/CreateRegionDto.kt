package io.directional.wine.domain.region.dto

class CreateRegionDto(
        var nameKorean: String,
        var nameEnglish: String,
        var parentId: Long?,
)
