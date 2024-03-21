package io.directional.wine.domain.region

import io.directional.wine.common.StringUtil
import io.directional.wine.common.exception.NotFoundException
import io.directional.wine.domain.region.dto.*
import io.directional.wine.domain.region.repository.RegionRepository
import io.directional.wine.domain.wine.dto.SimpleWineDto
import io.directional.wine.domain.winery.Winery
import io.directional.wine.domain.winery.WineryRepository
import io.directional.wine.domain.winery.dto.SimpleWineryDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RegionService(private val wineryRepository: WineryRepository,
                    private val regionRepository: RegionRepository) {

    fun readOne(id: Long): Region {
        return regionRepository.findById(id)
                .orElseThrow { NotFoundException() }
    }

    fun create(dto: CreateRegionDto): Region {
        val parentRegionV2 = dto.parentId?.let { readOne(it) }
        val region = regionRepository.save(Region(dto, parentRegionV2))
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
        regionRepository.delete(region)
    }

    fun findAllRegionWithNoParent(): List<Region> {
        return regionRepository.findAllByParentIsNull()
    }


    fun findByRequest(request: RegionSearchRequestDto): List<RegionSearchResponseDto> {
        val response: MutableList<RegionSearchResponseDto> = ArrayList()
        val regionResult: List<Region> = findRegions(request) //1. 지역을 찾아온다.
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
                response.add(RegionSearchResponseDto(region, groupedGrapes.get(region.id), groupedWinery.get(region.id)))
            }
        }
        return sortRegionSearchResponseDto(response, request.sort) //3. 찾아온 정보를 정렬한다.
    }

    private fun sortRegionSearchResponseDto(list: List<RegionSearchResponseDto>, sort: RegionNameSort): List<RegionSearchResponseDto> {
        return when (sort) {
            RegionNameSort.ENG_ASC -> list.sortedWith(compareBy { it.region.nameEnglish })
            RegionNameSort.ENG_DESC -> list.sortedWith(compareByDescending { it.region.nameEnglish })
            RegionNameSort.KOR_ASC -> list.sortedWith(compareBy { it.region.nameKorean })
            RegionNameSort.KOR_DESC -> list.sortedWith(compareByDescending { it.region.nameKorean })
            else -> list//do nothing (now: it.region.id asc)
        }
    }

    private fun findRegions(request: RegionSearchRequestDto): List<Region> {
        val koreanKey: String? = if (StringUtil.isKoreanOnly(request.key)) request.key else null
        val englishKey: String? = if (StringUtil.isKoreanOnly(request.key)) null else request.key
        return regionRepository.findRegions(englishKey, koreanKey, request.parentRegionId)
    }

    fun findOneRegionInfo(regionId: Long): RegionSearchOneDto {
        val region: Region = regionRepository.findRegionById(regionId) ?: throw NotFoundException()
        val grapes: List<GrapeSimpleDto> = regionRepository.findGrapesBy(listOf(regionId))
        val wineInfosMap : Map<Long, List<WineryWithWinesDto>> = regionRepository.findWineryAndWineByRegionId(regionId).groupBy { it.wineryId }
        return RegionSearchOneDto(region, grapes, convertWineryWithWine(wineInfosMap))
        }

    private fun convertWineryWithWine(mapGroupedByWineryId: Map<Long, List<WineryWithWinesDto>>): List<SimpleWineryDto> {
        return mapGroupedByWineryId.map { (wineryId, wineryList) ->
            val firstElement = wineryList.first()
            val wines = wineryList.map { wine ->
                SimpleWineDto(
                        wineId = wine.wineId,
                        wineNameKorean = wine.wineNameKorean,
                        wineNameEnglish = wine.wineNameEnglish,
                        wineWineryId = wine.wineWineryId,
                        wineRegionId = wine.wineRegionId
                )
            }
            SimpleWineryDto(
                    wineryId = firstElement.wineryId,
                    wineryNameKorean = firstElement.wineryNameKorean,
                    wineryNameEnglish = firstElement.wineryNameEnglish,
                    wineryRegionId = firstElement.wineryRegionId,
                    wines = wines
            )
        }.sortedBy { it.wineryId } // wineryId를 기준으로 ASC 정렬
    }
}






