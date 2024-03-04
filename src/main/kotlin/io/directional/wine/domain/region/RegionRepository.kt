package io.directional.wine.domain.region

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository  : JpaRepository<Region, Long> {
}