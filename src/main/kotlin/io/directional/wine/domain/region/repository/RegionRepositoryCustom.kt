package io.directional.wine.domain.region.repository

interface RegionRepositoryCustom {
    fun findAllRegionNamesDistinct(): List<String>
}