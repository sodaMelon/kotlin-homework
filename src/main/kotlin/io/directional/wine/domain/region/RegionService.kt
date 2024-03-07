package io.directional.wine.domain.region

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


    fun findAllRegionNames(): List<String>  {
        return regionRepository.findAllRegionNamesDistinct()
    }
}
