package io.directional.wine.domain.region.repository

import io.directional.wine.domain.region.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository  : JpaRepository<Region, Long>, RegionRepositoryCustom {
}