package io.directional.wine.domain.region.dto

class UpdateRegionDto(
        var id: Long,
        var nameKorean: String,
        var nameEnglish: String,
        var parentId: Long?,
)
