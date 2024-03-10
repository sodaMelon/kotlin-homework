package io.directional.wine.domain.region.repository

import io.directional.wine.domain.region.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository  : JpaRepository<Region, Long>, RegionRepositoryCustom {
    fun findRegionsByNameKoreanContainsOrderByNameKoreanAsc(koreanNameKeyword: String): List<Region>
    fun findRegionsByNameKoreanContainsOrderByNameKoreanDesc(koreanNameKeyword: String): List<Region>

    fun findRegionsByParentNameKoreanAndNameKoreanContainsOrderByNameKoreanAsc(parentName: String, koreanNameKeyword: String) : List<Region>
    fun findRegionsByParentNameKoreanAndNameKoreanContainsOrderByNameKoreanDesc(parentName: String, koreanNameKeyword: String) : List<Region>

}