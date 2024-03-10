package io.directional.wine.domain.region

import io.directional.wine.domain.region.dto.RegionSearchRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/region")
class RegionController(private val regionService: RegionService) {

    @GetMapping
    fun readOneRegion(@RequestParam id: Long): ResponseEntity<Any> =
            regionService.readOne(id)?.let { result ->
                ResponseEntity.ok(result)
            } ?: ResponseEntity.noContent().build()
    
    @PostMapping
    fun createNewRegion(@RequestBody region: Region): ResponseEntity<Any> = if (region.id != null) {
        ResponseEntity.badRequest().build()
    } else {
        ResponseEntity.ok(regionService.create(region))
    }

    @PatchMapping
    fun patchNewRegion(@RequestBody region: Region): ResponseEntity<Any> = if (region.id == null) {
        ResponseEntity.badRequest().build()
    } else {
        regionService.update(region)
        ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteRegion(@RequestParam id: Long): ResponseEntity<Any> = regionService.delete(id).run {
        ResponseEntity.noContent().build()
    }

    /**
     * search api 사용 시 , parentRegion 확인용
     * */
    @GetMapping("/find-all-region")
    fun findAllRegionNames(): ResponseEntity<Any> {
        return ResponseEntity.ok(regionService.findAllRegionNames())
    }

    /***
     * 지역조회1: 다수 조회
     */
    @PostMapping("/search-many")
    fun searchRegions(@RequestBody request: RegionSearchRequestDto) : ResponseEntity<Any>{
        if (request.isKeyKorean()) {
            return ResponseEntity.ok(regionService.findByRequest(request))
        }
        return ResponseEntity.badRequest().build()
    }


    /**
     * 지역조회2: 단일 조회 (by regionId)
     */
    @GetMapping("/search-one/{regionId}")
    fun searchRegionById(@PathVariable regionId : Long) : ResponseEntity<Any>{
        return ResponseEntity.ok(regionService.findOneRegionInfo(regionId))
    }

}