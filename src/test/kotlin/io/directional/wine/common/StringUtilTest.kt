package io.directional.wine.common


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class StringUtilTest{

    @Test
    fun main() {
        val stringUtil = StringUtil()

        val string1 = "Hello, 한영혼합 비허용"
        val string2 = "한글조합허용"
        val string3 = "12345"
        val string4 = "공백 포함 한글 조합 허용"

        assertFalse(stringUtil.isKoreanOnly(string1))
        assertTrue(stringUtil.isKoreanOnly(string2))
        assertFalse(stringUtil.isKoreanOnly(string3))
        assertTrue(stringUtil.isKoreanOnly(string4))
    }

}