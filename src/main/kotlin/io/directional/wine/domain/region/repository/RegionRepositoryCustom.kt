package io.directional.wine.domain.region.repository

import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.RegionSearchOneDto

interface RegionRepositoryCustom {
    fun findAllRegionNamesDistinct(): List<String>
    fun findOneRegionInfo(regionId: Long) : List<RegionSearchOneDto>
    fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto>
}