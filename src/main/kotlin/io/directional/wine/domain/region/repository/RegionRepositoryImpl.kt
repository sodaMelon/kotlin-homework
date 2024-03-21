package io.directional.wine.domain.region.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.domain.grape.QGrape.grape
import io.directional.wine.domain.grapeshare.QGrapeShare.grapeShare
import io.directional.wine.domain.region.QRegion
import io.directional.wine.domain.region.Region
import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.QGrapeSimpleDto
import io.directional.wine.domain.region.dto.QWineryWithWinesDto
import io.directional.wine.domain.region.dto.WineryWithWinesDto
import io.directional.wine.domain.wine.QWine.wine
import io.directional.wine.domain.winery.QWinery.winery

class RegionRepositoryImpl(private val queryFactory: JPAQueryFactory)  : RegionRepositoryCustom {

    override fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto> {
        return queryFactory
                .select(QGrapeSimpleDto(grape.id, grape.nameKorean, grape.nameEnglish, grapeShare.region.id))
                .from(grapeShare)
                .innerJoin(grape).on(grapeShare.grape.id.eq(grape.id))
                .where(grapeShare.region.id.`in`(regionIds)).fetch()
    }


    override fun findRegions(englishName: String?, koreanName: String?, parentId: Long?): MutableList<Region> {
        val regionV2 = QRegion.region
        val parent = QRegion.region.parent

        val query = queryFactory
                .select(regionV2, parent)
                .from(regionV2)
                .leftJoin(parent).fetchJoin() // JOIN FETCH 사용

        englishName?.let {
            query.where(regionV2.nameEnglish.contains(englishName))
        }
        koreanName?.let {
            query.where(regionV2.nameKorean.contains(koreanName))
        }
        parentId?.let {
            query.where(parent.id.eq(parentId))
        }

        val results = query.fetch()

        // 결과를 Region 엔티티로 매핑하여 반환
        return results.map { tuple ->
            val region = tuple.get(regionV2)
            val parentRegion = tuple.get(parent)
            region!!.parent = parentRegion // 부모 엔티티 설정
            region // 매핑된 Region 엔티티 반환
        }.toMutableList()
    }
    override fun findRegionById(regionId: Long):  Region? {
        val regionV2 = QRegion.region
        val parent = QRegion.region.parent

        val query = queryFactory
                .select(regionV2, parent)
                .from(regionV2)
                .leftJoin(parent).fetchJoin() // JOIN FETCH 사용, 부모 없는 경우도 있으므로 left join 사용해야함.

        regionId.let {
            query.where(regionV2.id.eq(regionId))
        }
        val results = query.fetchOne()

        // 결과를 Region 엔티티로 매핑하여 반환
        return results?.let { tuple ->
            val region = tuple.get(regionV2)
            val parentRegion = tuple.get(parent)
            region?.parent = parentRegion // 부모 엔티티 설정
            region // 매핑된 Region 엔티티 반환
        }
    }

    override fun findWineryAndWineByRegionId(regionId: Long):  List<WineryWithWinesDto> {
        val query = queryFactory
                .select(QWineryWithWinesDto(winery.id, winery.nameKorean, winery.nameEnglish, winery.regionId,
                        wine.id, wine.nameKorean, wine.nameEnglish, wine.wineryId, wine.regionId))
                .from(winery)
                .leftJoin(wine).on(winery.id.eq(wine.wineryId)).where(winery.regionId.eq(regionId)) // JOIN FETCH 사용, 부모 없는 경우도 있으므로 left join 사용해야함.
        return query.fetch()
        }

}