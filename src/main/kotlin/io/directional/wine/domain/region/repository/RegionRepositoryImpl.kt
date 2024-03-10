package io.directional.wine.domain.region.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.domain.grape.Grape
import io.directional.wine.domain.grape.QGrape
import io.directional.wine.domain.grape.QGrape.grape
import io.directional.wine.domain.grapeshare.QGrapeShare.grapeShare
import io.directional.wine.domain.region.QRegion.region
import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.QGrapeSimpleDto
import io.directional.wine.domain.region.dto.QRegionSearchOneDto
import io.directional.wine.domain.region.dto.RegionSearchOneDto
import io.directional.wine.domain.wine.QWine.wine
import io.directional.wine.domain.winery.QWinery.winery

class RegionRepositoryImpl(private val queryFactory: JPAQueryFactory)  : RegionRepositoryCustom {
    override fun findAllRegionNamesDistinct(): List<String> {
        val result = queryFactory.select(region.nameKorean).distinct()
                .from(region)
                .orderBy(region.nameKorean.asc())
                .fetch()


        return result
    }

    override fun findOneRegionInfo(regionId: Long): List<RegionSearchOneDto> {
        return queryFactory
                .select(QRegionSearchOneDto(region.id, region.nameKorean, region.nameEnglish, region.parentNameKorean, region.parentNameEnglish,
                        grape.id, grape.nameKorean, grape.nameEnglish,
                        wine.id, wine.nameKorean, wine.nameEnglish,
                        winery.id, winery.nameKorean, winery.nameEnglish))
                .from(region)
                .leftJoin(grapeShare).on(region.id.eq(grapeShare.regionId))
                .leftJoin(grape).on(grapeShare.grapeId.eq(grape.id))
                .leftJoin(wine).on(region.id.eq(wine.regionId))
                .leftJoin(winery).on(wine.wineryId.eq(winery.id))
                .where(region.id.eq(regionId)).fetch();
    }

    override fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto> {
        return queryFactory
                .select(QGrapeSimpleDto(grape.id, grape.nameKorean, grape.nameEnglish, grapeShare.regionId))
                .from(grapeShare)
                .innerJoin(grape).on(grapeShare.grapeId.eq(grape.id))
                .where(grapeShare.regionId.`in`(regionIds)).fetch()
    }

}