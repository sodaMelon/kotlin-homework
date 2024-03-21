package io.directional.wine.domain.region.repository

import io.directional.wine.domain.region.Region
import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.WineryWithWinesDto

interface RegionRepositoryCustom {
    fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto>

    fun findRegions(englishName: String?, koreanName: String?, parentId: Long?): MutableList<Region>
    fun findRegionById(regionId: Long):  Region?
    fun findWineryAndWineByRegionId(regionId: Long):  List<WineryWithWinesDto>
}