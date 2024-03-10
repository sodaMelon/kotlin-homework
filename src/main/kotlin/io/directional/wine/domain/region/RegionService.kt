package io.directional.wine.domain.region

import io.directional.wine.common.StringUtil
import io.directional.wine.domain.region.dto.*
import io.directional.wine.domain.region.repository.RegionRepository
import io.directional.wine.domain.winery.Winery
import io.directional.wine.domain.winery.WineryRepository
import org.springframework.stereotype.Service

@Service
class RegionService(private val regionRepository: RegionRepository,
                    private val wineryRepository: WineryRepository) {

    fun readOne(id:Long): Region? {
        regionRepository.findById(id).orElse(null)?.let{ region ->
            return region
        }
       return null
    }

    fun create(region: Region): Any {
        regionRepository.save(region)
        return region;
    }

    fun update(newInfo: Region) {
        newInfo.id?.let { id ->
            regionRepository.findById(id).orElse(null)?.let { currentRegion ->
                currentRegion.update(newInfo)
                regionRepository.save(currentRegion)
            }
        }
    }

    fun delete(id: Long) {
        regionRepository.findById(id).orElse(null)?.let { region ->
            regionRepository.delete(region)
        }
    }


    fun findAllRegionNames(): List<Region>  {
        return regionRepository.findAll() //todo 나중에 엔티티의 del값으로 데이터 정합성 여부 체크하게되면, del=false인것만  추출
    }

    fun findOneRegionInfo(regionId: Long): List<RegionSearchOneDto> {
        return regionRepository.findOneRegionInfo(regionId)
    }

    fun findByRequest(request: RegionSearchRequestDto) :  List<RegionSearchResponseDto>? {
        val response : MutableList<RegionSearchResponseDto> = ArrayList()
        val regionResult : List<Region> = findRegions(request)
        if (regionResult.isNotEmpty()){
            val regionIds : MutableList<Long> = ArrayList()
            regionResult.forEach { region -> region.id?.let { regionIds.add(it) }   }
            // List<GrapeSimpleDto>를 regionId를 기준으로 그룹화하여 Map에 넣는다.
            val groupedGrapes: Map<Long, List<GrapeSimpleDto>> = regionRepository.findGrapesBy(regionIds)
                    .groupBy { it.regionId }
            // List<Winery>를 regionId를 기준으로 그룹화하여 Map에 넣는다.
            val groupedWinery : Map<Long, List<Winery>> = wineryRepository.findByRegionIdIn(regionIds)
                    .groupBy { it.regionId }
            regionResult?.forEach { region ->
                response.add(RegionSearchResponseDto(region, groupedGrapes.get(region.id), groupedWinery.get(region.id)))
            }
        }
        return response
    }

    private fun findRegions(request: RegionSearchRequestDto) : List<Region> {
        return when (request.sort) {
            RegionNameSort.KOR_ASC -> {
                if (request.parentRegionName == null) {
                    regionRepository.findRegionsByNameKoreanContainsOrderByNameKoreanAsc(request.key)
                } else {
                    regionRepository.findRegionsByParentNameKoreanAndNameKoreanContainsOrderByNameKoreanAsc(
                            request.parentRegionName,
                            request.key
                    )
                }
            }
            RegionNameSort.KOR_DESC -> {
                if (request.parentRegionName == null) {
                    regionRepository.findRegionsByNameKoreanContainsOrderByNameKoreanDesc(request.key)
                } else {
                    regionRepository.findRegionsByParentNameKoreanAndNameKoreanContainsOrderByNameKoreanDesc(
                            request.parentRegionName,
                            request.key
                    )
                }
            }
            else -> {
                // 영어 이름 검색 미지원. (일단 기본값 세팅) //todo fix
                regionRepository.findRegionsByNameKoreanContainsOrderByNameKoreanAsc(request.key)
            }
        }
    }

    private fun regionsMap(){

    }
}
