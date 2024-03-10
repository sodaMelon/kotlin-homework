package io.directional.wine.domain.winery

import org.springframework.data.jpa.repository.JpaRepository

interface WineryRepository : JpaRepository<Winery, Long> {
    fun findByRegionIdIn(regionIds : List<Long>) : List<Winery>
}