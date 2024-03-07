package io.directional.wine.domain.region

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
     * search api 사용 시 , filter1 선택용
     * */
    @GetMapping("/find-all-region-names")
    fun findAllRegionNames(): ResponseEntity<Any> {
        return ResponseEntity.ok(regionService.findAllRegionNames())
    }

}