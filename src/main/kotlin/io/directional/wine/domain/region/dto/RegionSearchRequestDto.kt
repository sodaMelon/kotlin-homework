package io.directional.wine.domain.region.dto

import io.directional.wine.common.StringUtil

data class RegionSearchRequestDto(
        val parentRegionName: String?,
        val sort: RegionNameSort,
        val key: String
) {

    /**
     * 현재는 한글 검색만 지원하므로 임시로 이 function을 사용한다.
     */
    fun isKeyKorean() : Boolean {
        return StringUtil.isKoreanOnly(this.key)
    }
}