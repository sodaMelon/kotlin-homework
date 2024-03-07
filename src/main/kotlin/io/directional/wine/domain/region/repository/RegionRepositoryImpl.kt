package io.directional.wine.domain.region.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.domain.region.QRegion.region

class RegionRepositoryImpl(private val queryFactory: JPAQueryFactory)  : RegionRepositoryCustom {
    override fun findAllRegionNamesDistinct(): List<String> {
        val result = queryFactory.select(region.nameKorean).distinct()
                .from(region)
                .orderBy(region.nameKorean.asc())
                .fetch()
        return result
    }
}