package io.directional.wine.domain.region.repository

import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.WineryWithWinesDto
import io.directional.wine.domain.regionv2.RegionV2

interface RegionRepositoryCustom {
    fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto>

    fun findRegions(englishName: String?, koreanName: String?, parentId: Long?): MutableList<RegionV2>
    fun findRegionById(regionId: Long):  RegionV2?
    fun findWineryAndWineByRegionId(regionId: Long):  List<WineryWithWinesDto>
}