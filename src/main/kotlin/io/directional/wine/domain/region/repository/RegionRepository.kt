package io.directional.wine.domain.region.repository

import io.directional.wine.domain.regionv2.RegionV2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository  : JpaRepository<RegionV2, Long>, RegionRepositoryCustom {
}