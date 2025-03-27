package com.biritum.pdf

import kotlin.test.Test
import kotlin.test.assertEquals

class PdfTest {
    @Test
    fun stringEscaping() {
        assertEquals("(string)", escape("string"))
        assertEquals("(before\\\\after)", escape("before\\after"))
        assertEquals("(before\\(inside\\)after)", escape("before(inside)after"))
    }

    fun escape(s: String): String {
        var escaped = s
            .replace("\\", "\\\\")
            .replace("(", "\\(")
            .replace(")", "\\)")
        return "($escaped)"
    }

}

