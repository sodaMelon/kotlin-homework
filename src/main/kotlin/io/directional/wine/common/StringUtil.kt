package io.directional.wine.common

class StringUtil {
    fun isKoreanOnly(input: String): Boolean {
        val koreanRegex = Regex("[가-힣\\s]+") //한글, 공백 포함
        return koreanRegex.matches(input)
    }
}