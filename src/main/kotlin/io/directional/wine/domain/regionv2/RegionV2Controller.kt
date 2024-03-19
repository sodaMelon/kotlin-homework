package io.directional.wine.domain.regionv2

import io.directional.wine.domain.region.dto.CreateRegionDto
import io.directional.wine.domain.region.dto.UpdateRegionDto
import io.directional.wine.domain.regionv2.dto.RegionV2SearchRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/regions-v2")
class RegionV2Controller(private val regionService: RegionV2Service) {

    @GetMapping
    fun readOneRegion(@RequestParam id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(regionService.readOne(id))
    }

    @PostMapping
    fun createNewRegion(@RequestBody dto: CreateRegionDto): ResponseEntity<Any> {
        val result = regionService.create(dto)
        return ResponseEntity.created(URI.create("/regions-v2/${result.id}")).build()
        //fixme 분산DB 환경이라면 상황에 따라서 200 반환으로 변경
    }

    @PatchMapping
    fun patchNewRegion(@RequestBody dto: UpdateRegionDto): ResponseEntity<Any> {
        regionService.update(dto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteRegion(@RequestParam id: Long): ResponseEntity<Any> = regionService.delete(id).run {
        ResponseEntity.noContent().build()
    }


    /**
     * search api 사용 시 , parentRegion 지정용. (region.parent가 없으면 최상위 부모 지역임)
     * */
    @GetMapping("/no-parent/names")
    fun findAllRegionNames(): ResponseEntity<Any> {
        return ResponseEntity.ok(regionService.findAllRegionWithNoParent())
    }

    /***
     * 지역조회1: 다수 조회 (v2에서는 한글/영문 모두 검색가능)
     */
    @PostMapping("/search")
    fun searchRegions(@RequestBody request: RegionV2SearchRequestDto) : ResponseEntity<Any>{
            return ResponseEntity.ok(regionService.findByRequest(request))
    }


}