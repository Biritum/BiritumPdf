package com.biritum.pdf.text

import com.biritum.pdf.MockContext
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.line.Pen
import kotlin.test.Test
import kotlin.test.assertEquals

class TextTest {
    @Test
    fun drawText() {
        val text = Text("A (small) text containing %,[]{}", Font(BaseFont.TimesRoman, 12), Point(10.mm, 20.mm), Pen(0xFF00FF, 2.1))

        val actual = text.pdfContent(MockContext())
        val expected = "1.0 0.0 1.0 rg 2.1 w BT /Font1 12 Tf 28 519 Td (A (small) text containing %,[]{}) Tj ET f"

        assertEquals(expected, actual)
    }
}