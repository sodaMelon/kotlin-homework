package io.directional.wine.common


import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class StringUtilTest{

    @Test
    fun main() {
        val string1 = "Hello, 한영혼합 비허용"
        val string2 = "한글조합허용"
        val string3 = "12345"
        val string4 = "공백 포함 한글 조합 허용"

        assertFalse(StringUtil.isKoreanOnly(string1))
        assertTrue(StringUtil.isKoreanOnly(string2))
        assertFalse(StringUtil.isKoreanOnly(string3))
        assertTrue(StringUtil.isKoreanOnly(string4))
    }

}