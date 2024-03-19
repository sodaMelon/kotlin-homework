package io.directional.wine.domain.regionv2.repository

import io.directional.wine.domain.regionv2.RegionV2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionV2Repository : JpaRepository<RegionV2, Long> {
    fun findAllByParentIsNull(): List<RegionV2>

}