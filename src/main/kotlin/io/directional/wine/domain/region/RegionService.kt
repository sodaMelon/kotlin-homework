package io.directional.wine.domain.region

import io.directional.wine.domain.region.dto.RegionSearchOneDto
import io.directional.wine.domain.region.repository.RegionRepository
import org.springframework.stereotype.Service

@Service
class RegionService(private val regionRepository: RegionRepository) {

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
}
