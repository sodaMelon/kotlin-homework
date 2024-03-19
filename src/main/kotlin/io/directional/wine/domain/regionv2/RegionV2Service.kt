package io.directional.wine.domain.regionv2

import io.directional.wine.common.StringUtil
import io.directional.wine.common.exception.NotFoundException
import io.directional.wine.domain.region.dto.CreateRegionDto
import io.directional.wine.domain.region.dto.GrapeSimpleDto
import io.directional.wine.domain.region.dto.RegionNameSort
import io.directional.wine.domain.region.dto.UpdateRegionDto
import io.directional.wine.domain.region.repository.RegionRepository
import io.directional.wine.domain.regionv2.dto.RegionV2SearchRequestDto
import io.directional.wine.domain.regionv2.dto.RegionV2SearchResponseDto
import io.directional.wine.domain.regionv2.repository.RegionV2Repository
import io.directional.wine.domain.winery.Winery
import io.directional.wine.domain.winery.WineryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RegionV2Service(private val regionV2Repository: RegionV2Repository,
                      private val wineryRepository: WineryRepository,
                      private val regionRepository: RegionRepository) {

    fun readOne(id: Long): RegionV2 {
        return regionV2Repository.findById(id)
                .orElseThrow { NotFoundException() }
    }

    fun create(dto: CreateRegionDto): RegionV2 {
        val parentRegionV2 = dto.parentId?.let { readOne(it) }
        val region = regionV2Repository.save(RegionV2(dto, parentRegionV2))
        return region
    }

    @Transactional
    fun update(dto: UpdateRegionDto) {
        val targetRegion = readOne(dto.id)
        targetRegion.updateNames(dto)

        dto.parentId?.let { parentId ->
            if (parentId != targetRegion.parent?.id) {
                val newParent = readOne(parentId)
                targetRegion.updateParent(newParent)
            }
        } ?: targetRegion.updateParent(null)
        //더티 체킹되므로 save 필요x
    }

    fun delete(id: Long) {
        val region = readOne(id)
        regionV2Repository.delete(region)
    }

    fun findAllRegionWithNoParent(): List<RegionV2> {
        return regionV2Repository.findAllByParentIsNull()
    }


    fun findByRequest(request: RegionV2SearchRequestDto): List<RegionV2SearchResponseDto> {
        val response: MutableList<RegionV2SearchResponseDto> = ArrayList()
        val regionResult: List<RegionV2> = findRegions(request) //1. 지역을 찾아온다.
        if (regionResult.isNotEmpty()) { //2. 지역값으로 추가정보를 찾아온다.
            val regionIds : MutableList<Long> = ArrayList()
            regionResult.forEach { region -> region.id?.let { regionIds.add(it) }   }
            // List<GrapeSimpleDto>를 regionId를 기준으로 그룹화하여 Map에 넣는다.
            val groupedGrapes: Map<Long, List<GrapeSimpleDto>> = regionRepository.findGrapesBy(regionIds)
                    .groupBy { it.regionId }
            // List<Winery>를 regionId를 기준으로 그룹화하여 Map에 넣는다.
            val groupedWinery: Map<Long, List<Winery>> = wineryRepository.findByRegionIdIn(regionIds)
                    .groupBy { it.regionId }
            regionResult.forEach { region ->
                response.add(RegionV2SearchResponseDto(region, groupedGrapes.get(region.id), groupedWinery.get(region.id)))
            }
        }
        return sortRegionSearchResponseDto(response, request.sort) //3. 찾아온 정보를 정렬한다.
    }

    private fun sortRegionSearchResponseDto(list: List<RegionV2SearchResponseDto>, sort: RegionNameSort): List<RegionV2SearchResponseDto> {
        return when (sort) {
            RegionNameSort.ENG_ASC -> list.sortedWith(compareBy { it.region.nameEnglish })
            RegionNameSort.ENG_DESC -> list.sortedWith(compareByDescending { it.region.nameEnglish })
            RegionNameSort.KOR_ASC -> list.sortedWith(compareBy { it.region.nameKorean })
            RegionNameSort.KOR_DESC -> list.sortedWith(compareByDescending { it.region.nameKorean })
            else -> list//do nothing (now: it.region.id asc)
        }
    }

    private fun findRegions(request: RegionV2SearchRequestDto): List<RegionV2> {
        val koreanKey: String? = if (StringUtil.isKoreanOnly(request.key)) request.key else null
        val englishKey: String? = if (StringUtil.isKoreanOnly(request.key)) null else request.key
        return regionRepository.findRegions(englishKey, koreanKey, request.parentRegionId)
    }
}






