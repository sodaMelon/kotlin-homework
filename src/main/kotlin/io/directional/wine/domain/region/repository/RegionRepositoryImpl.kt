package io.directional.wine.domain.region.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.domain.grape.QGrape.grape
import io.directional.wine.domain.grapeshare.QGrapeShare.grapeShare
import io.directional.wine.domain.region.QRegion.region
import io.directional.wine.domain.region.dto.*
import io.directional.wine.domain.regionv2.QRegionV2
import io.directional.wine.domain.regionv2.RegionV2
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
                .leftJoin(grapeShare).on(region.id.eq(grapeShare.region.id))
                .leftJoin(grape).on(grapeShare.grape.id.eq(grape.id))
                .leftJoin(wine).on(region.id.eq(wine.regionId))
                .leftJoin(winery).on(wine.wineryId.eq(winery.id))
                .where(region.id.eq(regionId)).fetch();
    }

    override fun findGrapesBy(regionIds: List<Long>): List<GrapeSimpleDto> {
        return queryFactory
                .select(QGrapeSimpleDto(grape.id, grape.nameKorean, grape.nameEnglish, grapeShare.region.id))
                .from(grapeShare)
                .innerJoin(grape).on(grapeShare.grape.id.eq(grape.id))
                .where(grapeShare.region.id.`in`(regionIds)).fetch()
    }


    override fun findRegions(englishName: String?, koreanName: String?, parentId: Long?): MutableList<RegionV2> {
        val regionV2 = QRegionV2.regionV2
        val parent = QRegionV2.regionV2.parent

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

        // 결과를 RegionV2 엔티티로 매핑하여 반환
        return results.map { tuple ->
            val region = tuple.get(regionV2)
            val parentRegion = tuple.get(parent)
            region!!.parent = parentRegion // 부모 엔티티 설정
            region // 매핑된 RegionV2 엔티티 반환
        }.toMutableList()
    }
    override fun findRegionById(regionId: Long):  RegionV2? {
        val regionV2 = QRegionV2.regionV2
        val parent = QRegionV2.regionV2.parent

        val query = queryFactory
                .select(regionV2, parent)
                .from(regionV2)
                .leftJoin(parent).fetchJoin() // JOIN FETCH 사용, 부모 없는 경우도 있으므로 left join 사용해야함.

        regionId.let {
            query.where(regionV2.id.eq(regionId))
        }
        val results = query.fetchOne()

        // 결과를 RegionV2 엔티티로 매핑하여 반환
        return results?.let { tuple ->
            val region = tuple.get(regionV2)
            val parentRegion = tuple.get(parent)
            region?.parent = parentRegion // 부모 엔티티 설정
            region // 매핑된 RegionV2 엔티티 반환
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